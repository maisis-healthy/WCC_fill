/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.maisis.healthy.pps2.persistencerim;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import healthy.Archetype;
import healthy.ObservationArchetype;
import javax.ws.rs.core.MediaType;

/**
 * Cliente REST para consumo dos serviços
 *
 * @author Carlos Cardoso <carlos.cardoso@maisis.pt>
 */
public class ClientREST {

    private static String getSrvPath = "by";
    private static String urlPath = "http://healthy.oobian.com/ArchetypeManagerRest/webresources/archetypes";
    private Archetype response;
    private Client client;
    ClientResponse responsea;

    /**
     * *
     * Médoto para escolha do arquétipo por ID
     *
     * @param id
     * @return Archetype
     */
    public Archetype getArchetypeId(int id) {

        client = Client.create();
        WebResource webResource = client.resource(urlPath + "/" + getSrvPath + "/" + id);
        response = webResource.accept(MediaType.APPLICATION_XML_TYPE).get(Archetype.class);
        return response;
    }

    /**
     * *
     * get da resposta(Arquétipo) do serviço
     *
     * @return Archetype
     * @see Archetype
     */
    public Archetype getResponse() {
        return response;
    }

    /**
     * Serviço para a persistência
     *
     * @param observationArchetype
     * @see ObservationArchetype
     */
    void saveRIM(ObservationArchetype observationArchetype) {
        WebResource webResourceRim =
                client.resource("http://healthy.oobian.com/RIMManager/webresources/observation");
        responsea = webResourceRim.type("application/xml").post(ClientResponse.class, observationArchetype);
    }

    /**
     * *
     * get da resposta(status) do serviço
     *
     * @return ClientResponse
     */
    public ClientResponse getResponseServer() {
        return responsea;
    }
}
