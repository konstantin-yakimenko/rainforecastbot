package com.jakimenko.rainforecastbot.service

import io.grpc.Status
import io.grpc.StatusException
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.slf4j.LoggerFactory
import ru.jakimenko.rainforecastbot.proto.MessageOuterClass
import ru.jakimenko.rainforecastbot.proto.TelegramMessageGrpc
import java.util.*


@GrpcService
class TelegramMessageService: TelegramMessageGrpc.TelegramMessageImplBase() {
    companion object {
        val logger = LoggerFactory.getLogger(TelegramMessageService::class.java)
    }

    val map = HashMap<String, MessageOuterClass.Update>()

    override fun saveUpdate(
        request: MessageOuterClass.Update?,
        responseObserver: StreamObserver<MessageOuterClass.ResponseId>?
    ) {

        val randomUUIDString = UUID.randomUUID().toString()
        map.put(randomUUIDString, request!!)

        logger.info("Item with messageId ${request.message.messageId} was added successful")

        val response = MessageOuterClass.ResponseId.newBuilder()
            .setId(randomUUIDString)
            .build()
        responseObserver!!.onNext(response)
        responseObserver.onCompleted()
    }

    override fun getUpdate(
        request: MessageOuterClass.ResponseId?,
        responseObserver: StreamObserver<MessageOuterClass.Update>?
    ) {
        val id = request!!.id
        if (map.containsKey(id)) {
            logger.info("Object with chatId ${map.get(id)!!.message.chat.id} is present")
            responseObserver!!.onNext(map.get(id))
            responseObserver.onCompleted();
        } else {
            responseObserver!!.onError(StatusException(Status.NOT_FOUND));
        }
    }
}
