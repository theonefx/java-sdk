/*
 * Copyright 2023 The Dapr Authors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
limitations under the License.
*/

package io.dapr.examples.workflows.childworkflow;

import io.dapr.workflows.client.DaprWorkflowClient;
import io.dapr.workflows.client.WorkflowInstanceStatus;

import java.util.concurrent.TimeoutException;

public class DemoChildWorkerflowClient {
  /**
   * The main method to start the client.
   *
   * @param args Input arguments (unused).
   * @throws InterruptedException If program has been interrupted.
   */
  public static void main(String[] args) {
    try (DaprWorkflowClient client = new DaprWorkflowClient()) {
      String instanceId = client.scheduleNewWorkflow(DemoWorkflow.class);
      System.out.printf("Started a new child-workflow model workflow with instance ID: %s%n", instanceId);
      WorkflowInstanceStatus workflowInstanceStatus =
          client.waitForInstanceCompletion(instanceId, null, true);

      String result = workflowInstanceStatus.readOutputAs(String.class);
      System.out.printf("workflow instance with ID: %s completed with result: %s%n", instanceId, result);

    } catch (TimeoutException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
