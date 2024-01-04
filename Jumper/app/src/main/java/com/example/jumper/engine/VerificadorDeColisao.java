package com.example.jumper.engine;


import com.example.jumper.elements.Passaro;
import com.example.jumper.elements.Pipes;

public class VerificadorDeColisao {

    private final Passaro passaro;
    private final Pipes canos;

    public VerificadorDeColisao( Passaro passaro, Pipes canos )
    {
        this.passaro = passaro;
        this.canos = canos;
    }

    public boolean temColisao()
    {
        return canos.temColisaoCom( passaro );
    }
}
