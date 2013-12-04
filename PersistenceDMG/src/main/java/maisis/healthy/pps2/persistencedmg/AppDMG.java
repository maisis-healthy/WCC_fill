/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package maisis.healthy.pps2.persistencedmg;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import healthy.PersonArchetype;
import healthy.rim.Person;
import healthy.rim.Role;
import healthy.rim.type.AD; //ad
import healthy.rim.type.AddressPurpose;
import healthy.rim.type.AddressType;
import healthy.rim.type.EN; //a
import healthy.rim.type.EntityNamePurpose;
import healthy.rim.type.EntityNameType;
import healthy.rim.type.II; //a
import healthy.rim.type.IVL; //a
import healthy.rim.type.TEL; //a
import healthy.rim.type.TS; //a
import healthy.rim.type.TelecomEquipment;
import healthy.rim.type.TelecomPurpose;
import healthy.rim.type.TelecomType;

/**
 *
 * @author Carlos Cardoso <carlos.cardoso@maisis.pt>
 */
public class AppDMG {

    public static void main(String[] args) {

        // Entity
        Person entity = new Person();
        // IDS
        entity.getEntityIds().add(new II("NIF", "888888888", "Numero de Identificação Fiscal"));
        //NAMES
        entity.getEntityNames().add(new EN(EntityNameType.BIRTHNAME, EntityNamePurpose.CORRESPONDENCE, Boolean.TRUE, Boolean.TRUE,
                "Carlos", "Emanuel", "Cardoso", null));
        //ADDRESSES 
        entity.getEntityAddresses().add(new AD("Rua...", "Lamego", "5100-303", "Portugal", AddressType.HOME, AddressPurpose.POSTAL, null));
        // TELECOMS 
        entity.getEntityTelecoms().add(new TEL(TelecomType.PHONE, TelecomEquipment.CELL, TelecomPurpose.PERSONAL, "912534251", "", new IVL<TS>(new TS(), new TS())));
        // ROLES
        Role role = new Role();
        // IDS 
        role.getRoleIds().add(new II("REF", "A-3847547235", "Orden dos Medicos")); // quando PROFESSIONAL
        //NAMES
        role.getRoleNames().add(new EN(EntityNameType.BIRTHNAME, EntityNamePurpose.CORRESPONDENCE, Boolean.TRUE, Boolean.TRUE,
                "Carlos", "Emanuel", "Cardoso", null)); // QUANDO UTENTE
        //ADDRESSES 
        role.getRoleAddresses().add(new AD("Rua...", "Lamego", "5100", "Portugal", AddressType.WORKPLACE, AddressPurpose.POSTAL, null));
        // TELECOMS  
        role.getRoleTelecoms().add(new TEL(TelecomType.PHONE, TelecomEquipment.CELL, TelecomPurpose.BUSINESS, "912534251", "", new IVL<TS>(new TS(), new TS())));
        //adicionar a role
        entity.getRoles().add(role);
        
        // Instancia do objeto PersonArchetype  
        PersonArchetype personArchetype = new PersonArchetype(entity);
        //seriço para a persistencia
        try {
            Client client = Client.create();
            WebResource webResourceDmgRim = client.resource("http://healthy.oobian.com/EntityManager/webresources/person");
            ClientResponse response;
            response = webResourceDmgRim.type("application/xml").post(ClientResponse.class, personArchetype);
            System.out.println("Server Response: " + response.getStatus());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
