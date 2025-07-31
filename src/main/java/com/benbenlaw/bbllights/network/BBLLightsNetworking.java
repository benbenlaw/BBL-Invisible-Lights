package com.benbenlaw.bbllights.network;

import com.benbenlaw.bbllights.BBLLights;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class BBLLightsNetworking {

    public static void registerNetworking(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(BBLLights.MOD_ID);


        //To Server From Client
        registrar.playToServer(LightItemPacket.TYPE, LightItemPacket.STREAM_CODEC, LightItemPacket::handle);



    }
}
