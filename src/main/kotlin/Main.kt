import Level.layout
import Level.size
import dev.kord.common.entity.ButtonStyle
import dev.kord.core.Kord
import dev.kord.core.behavior.channel.createMessage
import dev.kord.core.behavior.edit
import dev.kord.core.behavior.interaction.respondPublic
import dev.kord.core.event.interaction.ButtonInteractionCreateEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import dev.kord.rest.builder.component.ActionRowBuilder
import dev.kord.rest.builder.message.create.actionRow
import dev.kord.rest.builder.message.modify.actionRow
import io.ktor.client.utils.*
import kotlin.math.abs

suspend fun main() {
    val kord = Kord({}.javaClass.getResource("token")?.readText()!!)

    kord.on<MessageCreateEvent> {
        if (message.author?.isBot != false) return@on

        when (message.content) {
            "!play" -> {
                message.channel.createMessage {
                    content = Level.generateMessage()
                    actionRow {
                        interactionButton(ButtonStyle.Primary, "left") {
                            label = "Lewo"
                        }
                        interactionButton(ButtonStyle.Primary, "up") {
                            label = "Góra"
                        }
                        interactionButton(ButtonStyle.Primary, "down") {
                            label = "Dół"
                        }
                        interactionButton(ButtonStyle.Primary, "right") {
                            label = "Prawo"
                        }
                    }
                }
            }
        }
    }

    kord.on<ButtonInteractionCreateEvent> {
        val layoutClone = Level.layout.toList()
        var colided = false
        for ((index, pole) in Level.layout.withIndex()) {
            if (pole == 1) {
                Level.layout[index] = 0
                var newIndex = index
                fun setNewIndex(move: Int): Int {
                    newIndex = index + move
                    return newIndex
                }
                when (interaction.componentId) {
                    "up" -> {
                        Level.layout[if (index - 5 >= 0) setNewIndex(-5) else index] = 1
                    }
                    "down" -> {
                        Level.layout[if (index + 5 < Level.size*Level.size) setNewIndex(5) else index] = 1
                    }
                    "left" -> {
                        Level.layout[if ((index - 1)%5 != 4) setNewIndex(-1) else index] = 1
                    }
                    "right" -> {
                        Level.layout[if ((index + 1)%5 != 0) setNewIndex(1) else index] = 1
                    }
                }

                // Move rocks
                if (layoutClone[newIndex] == 2) {
                    val moved = newIndex-index
                    if (abs(moved) == 1 && !(newIndex%5 == 0 || newIndex%5 == 4)) {
                        layout[newIndex + moved] = 2
                    } else if (abs(moved) == 5 && !(newIndex < Level.size || newIndex >= Level.size*4)) {
                        layout[newIndex + moved] = 2
                    } else {
                        colided = true
                        break
                    }

                    // Check if the box wasn't moved into another box
                    if (layoutClone[newIndex + moved] == 2) {
                        colided = true
                    }

                    // Check if box is inside goal
                    if (layoutClone[newIndex + moved] == 4) {
                        Level.layout[newIndex + moved] = 3
                    }
                }

                // Check for player collision
                if (layoutClone[newIndex] == 3) {
                    colided = true
                }

                break
            }
        }
        if (colided) {
            Level.layout = layoutClone.toMutableList()
        }

        interaction.message.edit {
            content = Level.generateMessage()
            actionRow {
                interactionButton(ButtonStyle.Primary, "left") {
                    label = "Lewo"
                }
                interactionButton(ButtonStyle.Primary, "up") {
                    label = "Góra"
                }
                interactionButton(ButtonStyle.Primary, "down") {
                    label = "Dół"
                }
                interactionButton(ButtonStyle.Primary, "right") {
                    label = "Prawo"
                }
            }
        }
        try {
            interaction.respondPublic {  }
        } catch (ignored: Exception) {}
    }

    kord.login {
        @OptIn(PrivilegedIntent::class)
        intents += Intent.MessageContent
    }
}