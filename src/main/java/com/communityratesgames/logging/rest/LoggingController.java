package com.communityratesgames.logging.rest;

import com.communityratesgames.logging.dao.DataAccessLocal;
import com.communityratesgames.logging.domain.Logging;
import com.communityratesgames.logging.domain.UserStatistic;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@NoArgsConstructor
@Stateless
@Path("/logs")
public class LoggingController {

    private final static Logger logger = Logger.getLogger(com.communityratesgames.logging.transactions.LoggingTransaction.class);

    @Inject
    private DataAccessLocal dal;

    @POST
    @Consumes("application/JSON")
    public Response addLog(String log) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Logging logs = mapper.readValue(log, Logging.class);
            Logging l = dal.addNewLog(logs);
            String temp = mapper.writeValueAsString(l);
            return Response.ok(temp).build();
        }catch (Exception e){
            return Response.status(413).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces("application/JSON")
    public Response showAllLogs() {
        try {
            List<Logging> result = dal.showAllLogs();
            return Response.ok(result).build();
        } catch ( Exception e ) {
            return Response.status(404).build();
        }
    }

    @GET
    @Produces("application/JSON")
    @Path("/search")
    public Response findUser(@QueryParam("name") String name ) {
        try {
            String result = dal.findUser(name);
            return Response.ok(result).build();
        } catch ( Exception e) {
            return Response.status(404).build();
        }
    }

    @POST
    @Produces("application/JSON")
    @Path("/statistic")
    public Response showStatisticAboutAUser(String name) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Logging logs = mapper.readValue(name, Logging.class);
            Long statistic = dal.showStatistic(logs);
            return Response.ok(statistic).build();
        } catch (Exception e ) {
            return Response.status(402).build();
        }
    }
}
