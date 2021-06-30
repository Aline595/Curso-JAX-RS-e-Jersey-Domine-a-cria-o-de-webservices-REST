package br.com.alura.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;

@Path("carrinhos") // Define a uri que ir� acessar o servidor
public class CarrinhoResource {

    @GET
    @Produces(MediaType.APPLICATION_XML)// Diz que est� roduzindo XML, pode ser qualquer formato json...
    public String busca() {
        Carrinho carrinho = new CarrinhoDAO().busca(1l);
        return carrinho.toXML();
    }

}