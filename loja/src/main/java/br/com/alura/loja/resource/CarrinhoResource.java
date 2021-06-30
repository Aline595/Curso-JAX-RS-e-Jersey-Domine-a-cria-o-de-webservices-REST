package br.com.alura.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;

@Path("carrinhos") // Define a uri que irá acessar o servidor
public class CarrinhoResource {
	
	
	//usando XMl
	@Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_XML)// Diz que está roduzindo XML, pode ser qualquer formato json...
    //public String busca(@QueryParam("id") long id) {
    public String busca(@PathParam("id") long id) {
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        return carrinho.toXML();
    }
	
	/*
	//Usando Json
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String busca(@PathParam("id") long id) {
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        return carrinho.toJson();
    }
	*/
	
    @POST // Envia requisição que cria novo carrinho, necessario XMl 
    @Produces(MediaType.APPLICATION_XML)
    public String adiciona(String conteudo) {
        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        new CarrinhoDAO().adiciona(carrinho);
        return "<status>sucesso</status>";
    }
}