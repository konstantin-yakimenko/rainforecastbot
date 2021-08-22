package com.jakimenko.rainforecastbot.dto.telegram

data class Update(
    val update_id: Int,
    val message: Message?,
    val edited_message: Message?,
    val channel_post: Message?,
    val edited_channel_post: Message?,
    val inline_query: InlineQuery?,
    val chosen_inline_result: ChosenInlineResult?,
    val callback_query: CallbackQuery?,
    val shipping_query: ShippingQuery?,
    val pre_checkout_query: PreCheckoutQuery?,
    val poll: Poll?,
    val poll_answer: PollAnswer?,
    val my_chat_member: ChatMemberUpdated?,
    val chat_member: ChatMemberUpdated?
)
