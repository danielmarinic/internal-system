package sk.itaps.portal.service.impl;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.logger.DefaultLogger;
import com.microsoft.graph.models.DirectoryObject;
import com.microsoft.graph.models.Group;
import com.microsoft.graph.models.Team;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.DirectoryObjectReferenceRequest;
import com.microsoft.graph.requests.GraphServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sk.itaps.portal.service.MicrosoftGraphService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class MicrosoftGraphServiceImpl implements MicrosoftGraphService {
    @Value("${azure.activedirectory.client-id}")
    private String clientId;

    @Value("${azure.activedirectory.client-secret}")
    private String clientSecret;

    @Value("${azure.activedirectory.tenant-id}")
    private String tenantId;

//    @Value("${azure.activedirectory.active-directory.scopes}")
    private final static String scope = "https://graph.microsoft.com/.default";
    private static List<String> scopes;

    private static ClientSecretCredential clientSecretCredential;
    private static TokenCredentialAuthProvider authProvider;
    private static GraphServiceClient graphClient;

    private static DefaultLogger logger = new DefaultLogger();

    public MicrosoftGraphServiceImpl() {

    }

    @PostConstruct
    public void initGraph() {
        clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();

        scopes = Arrays.asList(scope.split(","));

        authProvider = new TokenCredentialAuthProvider(scopes, clientSecretCredential);

        graphClient =
                GraphServiceClient
                        .builder()
                        .authenticationProvider(authProvider)
                        .logger(logger)
                        .buildClient();
    }

    public List<User> getAllUsers() {
        if (graphClient == null) throw new NullPointerException(
                "Graph client has not been initialized. Call initializeGraphAuth before calling this method");

        return graphClient.users()
                .buildRequest()
//                .select("birthday,userType,mail,companyName,department,country,city,streetAddress,postalCode")
//                .filter("userType eq 'Member'")
                .get()
                .getCurrentPage();

        //todo: pripravit aj na paging
//        EventCollectionPage eventPage = graphClient
//                .me()
//                .calendarView()
//                .buildRequest(options)
//                .select("subject,organizer,start,end")
//                .top(25)
//                .get();
//
//        List<Event> allEvents = new LinkedList<Event>();
//
//        // Create a separate list of options for the paging requests
//        // paging request should not include the query parameters from the initial
//        // request, but should include the headers.
//        List<Option> pagingOptions = new LinkedList<Option>();
//        pagingOptions.add(new HeaderOption("Prefer", "outlook.timezone=\"" + timeZone + "\""));
//
//        while (eventPage != null) {
//            allEvents.addAll(eventPage.getCurrentPage());+
//
//            EventCollectionRequestBuilder nextPage =
//                    eventPage.getNextPage();
//
//            if (nextPage == null) {
//                break;
//            } else {
//                eventPage = nextPage
//                        .buildRequest(pagingOptions)
//                        .get();
//            }
//        }
    }

    public User createUser(User user) {
//        User user = new User();
//        user.accountEnabled = true;
//        user.displayName = "Adele Vance";
//        user.mailNickname = "AdeleV";
//        user.userPrincipalName = "AdeleV@contoso.onmicrosoft.com";
//        PasswordProfile passwordProfile = new PasswordProfile();
//        passwordProfile.forceChangePasswordNextSignIn = true;
//        passwordProfile.password = "xWwvJ]6NMw+bWH-d";
//        user.passwordProfile = passwordProfile;

        return graphClient.users()
                .buildRequest()
                .post(user);
    }

    public void updateUser(User user) {
//        User user = new User();
//        LinkedList<String> businessPhonesList = new LinkedList<String>();
//        businessPhonesList.add("+1 425 555 0109");
//        user.businessPhones = businessPhonesList;
//        user.officeLocation = "18/2111";

        graphClient.me()
                .buildRequest()
                .patch(user);
    }

    public User findUser(String id) {
        return graphClient.users(id)
                .buildRequest()
                .get();
    }

    public byte[] getUserPhoto(String id) {
        try {
            InputStream stream =  graphClient.users(id)
                    .photo()
                    .content()
                    .buildRequest()
                    .get();
            //todo: dorobit check na stream().avilable()
            return stream.readAllBytes();
        } catch (ClientException e) {
            e.printStackTrace();
            logger.logDebug("User photo not found/");
            //todo: dorobit error handling
        } catch (IOException e) {
            e.printStackTrace();
            logger.logError("Fail read bytes from response.", e);
            //todo: dorobit error handling
        }
        return null;
    }

    public void uploadPhoto(byte[] stream, String id) {
        graphClient.users(id).photo().content()
                .buildRequest()
                .put(stream);
    }

    public List<Group> getAllGroups() {
        return graphClient.groups()
                .buildRequest()
//                .filter("groupTypes eq 'Security'")
                .get()
                .getCurrentPage();
    }

    public void addUserToGroup(String group, String user) {
        DirectoryObject directoryObject = new DirectoryObject();
        directoryObject.id = user;
        graphClient.groups(group)
                .members()
                .references()
                .buildRequest()
                .post(directoryObject);
    }

    public void addUserToGroupAsync(String group, String user) {
        DirectoryObject directoryObject = new DirectoryObject();
        directoryObject.id = user;
        graphClient.groups(group)
                .members()
                .references()
                .buildRequest()
                .postAsync(directoryObject);
    }

    public List<Team> getAllTeams() {
        return graphClient.teams()
                .buildRequest()
                .get()
                .getCurrentPage();
    }

    private DirectoryObjectReferenceRequest assignManagerRequest(String userId, String managerId) {
        return graphClient.users(userId)
                .manager()
                .reference()
                .buildRequest();
    }

    public void assignUserManager(String userId, String managerId) {
        DirectoryObject manager = new DirectoryObject();
        manager.id = managerId;
        assignManagerRequest(userId, managerId).put(manager);
    }

    public void assignUserManagerAsync(String userId, String managerId) {
        DirectoryObject manager = new DirectoryObject();
        manager.id = managerId;
        assignManagerRequest(userId, managerId).putAsync(manager);
    }
}
