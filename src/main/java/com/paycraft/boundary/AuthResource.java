package com.paycraft.boundary;

import com.paycraft.controller.AuthController;
import com.paycraft.controller.CryptoHelper;
import com.paycraft.controller.ProfileSyncHelper;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Path("/processor")
public class AuthResource {
    
    

    @Inject
    AuthController authController;

    @Inject
    CryptoHelper cryptoHelper;

    @Inject
    ProfileSyncHelper profileSyncHelper;

    @GET
    @Path("ping")
    @Produces(MediaType.TEXT_PLAIN)
    public Response doPing() {

        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("responseDesc", "I am alive and well (Authentication Service)");
        return Response.ok(job.build(), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("login")
    @Metered(name = "login_metered")
    @Timed(name = "login_timed")
    public Response doKeys(final String request) {
        return authController.doLogin(request); //Response.fromResponse(authController.doLogin(request)).build();
    }
    
    
    @POST
    @Path("user-login")
    @Metered(name = "login_metered")
    @Timed(name = "login_timed")
    public Response doUserLogin(final String request) {
        return authController.doUserLogin(request); //Response.fromResponse(authController.doLogin(request)).build();
    }
   
    @POST
    @Path("aboutme")
    public Response doAboutMe() {
        return Response.fromResponse(cryptoHelper.aboutME()).build();
    }

    @POST
    @Path("decrypt")
    public Response doDecrypt(final String requestStr) {
        return Response.fromResponse(cryptoHelper.doDecrypt(requestStr)).build();
    }

    @POST
    @Path("encrypt")
    public Response doLogin(final String request) {
        return Response.fromResponse(cryptoHelper.doEncrypt(request)).build();
    }

    /*
    @POST
    @Path("login/{iv}/{key}")
    public Response doLogin(final String request, @PathParam("iv") final String iv, @PathParam("key") final String key) throws UnsupportedEncodingException 
    { 
        
        System.out.println("iv = " + URLDecoder.decode(iv, StandardCharsets.UTF_8.toString())+"  key "+URLDecoder.decode(key, StandardCharsets.UTF_8.toString()));
        
        return Response.fromResponse(requestProcessor.processLoginRequest(request)).build();
    }
    
    
    @POST
    @Path("loginsys")
    public Response doLoginSys(final String request) 
    { 
        return Response.fromResponse(requestProcessor.processLoginRequest(request)).build();
    }
    
    @POST
    @Path("keys")
    public Response doKeys(final String request) 
    { 
        return Response.fromResponse(requestProcessor.processKeyRequest(request)).build();
    }
     */
    @POST
    @Path("reset")
    @Metered(name = "reset_metered")
    public Response doReset(@Valid final JsonObject request) {
        return authController.doReset(request);
    }
    
    @POST
    @Path("reset-own")
    @Metered(name = "reset_metered")
    public Response doResetOwn(@Valid final JsonObject request) {
        return authController.doResetOwnKeys(request);
    }

    @POST
    @Path("resend")
    public Response doResend(final String request) {
        return authController.doResend(request);
    }
    
    @POST
    @Path("refresh")
    public Response doRefresh(final String request) {
        return authController.doRefresh(request);
       
    }

    @POST
    @Path("sync-profile")
    public Response doProfileSync(final JsonObject request) {
      
        return profileSyncHelper.doLog(request); // return Response.fromResponse(profileSyncHelper.doLog(request)).build();
    }
    
    
    @POST
    @Path("sync-profile-wtp")
    public Response doProfileSyncWTP(final JsonObject request) {
      
        return profileSyncHelper.doLogWTP(request); // return Response.fromResponse(profileSyncHelper.doLog(request)).build();
    }
   
    @POST
    @Path("force-sync")
    public Response doForceSync(final JsonObject request) {
      
        return profileSyncHelper.doForceSync(request); // return Response.fromResponse(profileSyncHelper.doLog(request)).build();
    }
    
    @POST
    @Path("force-sync-txp")
    public Response doForceSyncTXP(final JsonObject request) {
      
        return profileSyncHelper.doForceSyncTxP(request); // return Response.fromResponse(profileSyncHelper.doLog(request)).build();
    }
    
    @POST
    @Path("sync-txp")
    public Response doSyncTXP(final JsonObject request) {
      
        return profileSyncHelper.doForceSyncTxP(request); // return Response.fromResponse(profileSyncHelper.doLog(request)).build();
    }
    
    @POST
    @Path("sync-profile-login")
    public Response doProfileSyncAndLogin(final JsonObject request) {
      
        return profileSyncHelper.doSyncProfileAndLogin(request); // return Response.fromResponse(profileSyncHelper.doLog(request)).build();
    }

    @POST
    @Path("maintain-profile")
    public Response doMaintain(@Valid final JsonObject request) {

        return profileSyncHelper.doSync(request);
    }
    
    @POST
    @Path("maintain-txp")
    public Response doMaintainTxp(@Valid final JsonObject request) {

        return profileSyncHelper.doSyncTxp(request);
    }

    @POST
    @Path("verify")
    public Response doVerify(@Valid final JsonObject verifyStr) {

        return profileSyncHelper.doVerify(verifyStr);
    }
    
    @POST
    @Path("verify-txp")
    public Response doVerifyTxp(@Valid final JsonObject verifyStr) {

        return profileSyncHelper.doVerifyTxp(verifyStr);
    }
    
    @POST
    @Path("verify-user")
    public Response doVerifyUser(@Valid final JsonObject verifyStr) {

        return profileSyncHelper.doVerify(verifyStr);
       
    }
    
    
    @POST
    @Path("create-client")
    public Response doCreateClient(final JsonObject request) {
        return authController.doLogClient(request);
    }
    
    @POST
    @Path("create-client/{id}")
    public Response doCreateClient(final @PathParam("id") long tid) {
        return authController.doLookupClient(tid);
    }

    @POST
    @Path("maintain-client")
    public Response doMaintainClient(@Valid final JsonObject request) {

       return authController.doSynClient(request);
    }
    
    @POST
    @Path("approve-client")
    public Response doApproveClient(@Valid final JsonObject request) {

       return authController.doApproveClient(request);
      
    }
    
    @POST
    @Path("delete-client")
    public Response doDeleteClient(@Valid final JsonObject request) {

       return authController.doDeleteClient(request);
    }
    
    @POST
    @Path("get-client-byname/{clientName}")
    public Response doGetClientByName(@Valid @PathParam("clientName") String clientName) {

      return authController.doLookupClient(clientName);
    }

    @POST
    @Path("list-clients")
    public Response doListClients() {

        return authController.doListTrimedClients();
    }

}
