package pt.maisis.healthy.pps2.persistencerim;

import healthy.Archetype;
import healthy.ObservationArchetype;
import healthy.rim.Observation;
import healthy.rim.Participation;
import healthy.rim.type.II;
import java.util.HashSet;
import java.util.Set;


/**
 * Classe principal para leitura e persistência no RIM
 * 
 * @author Carlos Cardoso <carlos.cardoso@maisis.pt>
 */
public class AppRIM {

    public static void main(String[] args) {
            //instanciar o cliente Web
            ClientREST clientREST = new ClientREST();
            //id do arquétipo a consumir 
            clientREST.getArchetypeId(18); // Arquétipo para o Peso
            //resposta com o objeto arquétipo 
            Archetype archetypeResponse = clientREST.getResponse();
            //método que preenche o campo data
            XMLfields fieldData = new XMLfields();
            fieldData.setXMLfields(archetypeResponse);         
            //atualizar o campo data com os dados preenchidos 
            archetypeResponse.setData(fieldData.getXmlData().asXML());            
            //preencher os Ids para os participantes na observação
            Participation part1 = new Participation();
            part1.setRoleInternalId(4L);
            Participation part2 = new Participation();
            part2.setRoleInternalId(5L);
            //adicionar os participantes criados
            Set<Participation> partSet = new HashSet(0);
            partSet.add(part1);
            partSet.add(part2);
            //preencher os IDs
            Set<II> ids = new HashSet<II>(0);
            ids.add(new II("id", "app_id", "Info_div"));
            //criar a observação com o contexto preenchido anteriormente         
            Observation obsContext = new Observation();
            obsContext.setActIds(ids);
            obsContext.setParticipations(partSet);
            //instanciar ObservationArchetype e passamos a resposta preenchida
            ObservationArchetype observationArchetype = 
                    new ObservationArchetype(archetypeResponse);
            //passamos o contexto preenchido
            observationArchetype.setContext(obsContext);
            //persistencia da obsevação
            clientREST.saveRIM(observationArchetype);          
            System.out.println(clientREST.getResponseServer());
    }
}

