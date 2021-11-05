/*
 *     This file is part of UnifiedMetrics.
 *
 *     UnifiedMetrics is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     UnifiedMetrics is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with UnifiedMetrics.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.cubxity.plugins.metrics.minestom.metric.events.server

import dev.cubxity.plugins.metrics.api.metric.collector.Collector
import dev.cubxity.plugins.metrics.api.metric.collector.CollectorCollection
import dev.cubxity.plugins.metrics.api.metric.collector.Counter
import dev.cubxity.plugins.metrics.common.metric.Metrics
import net.minestom.server.MinecraftServer
import net.minestom.server.event.EventListener
import net.minestom.server.event.server.ServerListPingEvent

class EventsServerCollection : CollectorCollection {
    // TODO: are these events async?
    private val pingCounter = Counter(Metrics.Events.ServerPing)

    private val pingListener = EventListener.of(ServerListPingEvent::class.java) { pingCounter.inc() }

    override val collectors: List<Collector> = listOf(pingCounter)

    override fun initialize() {
        with(MinecraftServer.getGlobalEventHandler()) {
            addListener(pingListener)
        }
    }

    override fun dispose() {
        with(MinecraftServer.getGlobalEventHandler()) {
            removeListener(pingListener)
        }
    }
}