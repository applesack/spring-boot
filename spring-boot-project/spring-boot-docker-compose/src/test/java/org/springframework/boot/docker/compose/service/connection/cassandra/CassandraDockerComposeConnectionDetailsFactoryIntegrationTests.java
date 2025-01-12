/*
 * Copyright 2012-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.docker.compose.service.connection.cassandra;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.cassandra.CassandraConnectionDetails;
import org.springframework.boot.autoconfigure.cassandra.CassandraConnectionDetails.Node;
import org.springframework.boot.docker.compose.service.connection.test.AbstractDockerComposeIntegrationTests;
import org.springframework.boot.testsupport.testcontainers.DockerImageNames;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for {@link CassandraDockerComposeConnectionDetailsFactory}.
 *
 * @author Scott Frederick
 */
class CassandraDockerComposeConnectionDetailsFactoryIntegrationTests extends AbstractDockerComposeIntegrationTests {

	CassandraDockerComposeConnectionDetailsFactoryIntegrationTests() {
		super("cassandra-compose.yaml", DockerImageNames.cassandra());
	}

	@Test
	void runCreatesConnectionDetails() {
		CassandraConnectionDetails connectionDetails = run(CassandraConnectionDetails.class);
		List<Node> contactPoints = connectionDetails.getContactPoints();
		assertThat(contactPoints.size()).isEqualTo(1);
		Node node = contactPoints.get(0);
		assertThat(node.host()).isNotNull();
		assertThat(node.port()).isGreaterThan(0);
		assertThat(connectionDetails.getUsername()).isNull();
		assertThat(connectionDetails.getPassword()).isNull();
		assertThat(connectionDetails.getLocalDatacenter()).isEqualTo("dc1");
	}

}
