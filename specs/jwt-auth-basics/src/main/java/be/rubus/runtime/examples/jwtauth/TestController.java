package be.rubus.runtime.examples.jwtauth;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.jwt.Claim;


@Path("/endp")
@RequestScoped
@RolesAllowed("Tester")
public class TestController {

    @Inject
    @Claim("custom-value")
    private String customValue;


    @GET
    @Path("/custom")
    public String getCustomValue() {
        return customValue;
    }


}
