package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import junit.framework.Assert;

import org.junit.Test;

public class ClienteTest {
 
    @Test // Define que é uma classe de teste
    public void testaQueAConexaoComOServidorFunciona() {
        Client client = ClientBuilder.newClient(); // Cria cliente para realizar a conexão
        WebTarget target = client.target("http://www.mocky.io"); // Define a URL base
        String conteudo = target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class); // Define o subdiretorio no site, get faz requisisão 
        //System.out.println(conteudo); // Exibe xml(dados da página)
        Assert.assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185")); // Define trecho que precisa ter no xml para estar correto 
    }
}