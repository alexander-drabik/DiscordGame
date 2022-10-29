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
        for ((index, pole) in Level.layout.withIndex()) {
            if (pole == 1) {
                Level.layout[index] = 0
                when (interaction.componentId) {
                    "up" -> {
                        Level.layout[if (index - 5 >= 0) index - 5 else index] = 1
                    }
                    "down" -> {
                        Level.layout[if (index + 5 < Level.size*Level.size) index + 5 else index] = 1
                    }
                    "left" -> {
                        Level.layout[if ((index - 1)%5 != 4) index - 1 else index] = 1
                    }
                    "right" -> {
                        Level.layout[if ((index + 1)%5 != 0) index + 1 else index] = 1
                    }
                }
                break
            }
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
        interaction.respondPublic {  }

    }

    kord.login {
        @OptIn(PrivilegedIntent::class)
        intents += Intent.MessageContent
    }
}