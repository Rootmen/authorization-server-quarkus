package ru.iedt.authorization.api.repository.users;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.RowSet;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;
import ru.iedt.authorization.models.UserInformation;
import ru.iedt.database.request.controller.DatabaseController;
import ru.iedt.database.request.controller.parameter.ParameterInput;
import ru.iedt.database.request.structures.nodes.v3.node.parameter.elements.primitives.type.ParameterLocalDate;

@Singleton
public class UserInfoRepository {

    @Inject
    DatabaseController databaseController;

    public Uni<UserInformation> addUserInfo(
        UUID userId,
        String userSurname,
        String userName,
        String userPatronymic,
        LocalDate userDateOfBirth,
        int userPersonalNumber,
        int userStructure,
        UUID userCurrentPost,
        String userPhone,
        String userOffice,
        PgPool client
    ) {
        HashMap<String, ParameterInput> parameters = new HashMap<>();
        parameters.put("ACCOUNT_UUID", new ParameterInput("ACCOUNT_UUID", userId.toString()));
        parameters.put("USER_SURNAME", new ParameterInput("USER_SURNAME", userSurname));
        parameters.put("USER_NAME", new ParameterInput("USER_NAME", userName));
        parameters.put("USER_PATRONYMIC", new ParameterInput("USER_PATRONYMIC", userPatronymic));
        parameters.put("USER_DATE_OF_BIRTH", new ParameterInput("USER_DATE_OF_BIRTH", userDateOfBirth.format(ParameterLocalDate.getFormatter())));
        parameters.put("USER_PERSONAL_NUMBER", new ParameterInput("USER_PERSONAL_NUMBER", Integer.toString(userPersonalNumber)));
        parameters.put("USER_STRUCTURE", new ParameterInput("USER_STRUCTURE", Integer.toString(userStructure)));
        parameters.put("USER_CURRENT_POST", new ParameterInput("USER_CURRENT_POST", userCurrentPost.toString()));
        parameters.put("USER_PHONE", new ParameterInput("USER_PHONE", userPhone));
        parameters.put("USER_OFFICE", new ParameterInput("USER_OFFICE", userOffice));
        return databaseController
            .runningQuerySet("USERS", "ADD_USERS_INFO", parameters, client)
            .onItem()
            .transform(element -> element.get(0).get("main"))
            .onItem()
            .transform(RowSet::iterator)
            .onItem()
            .transform(iterator -> iterator.hasNext() ? UserInformation.from(iterator.next()) : null);
    }

    public Uni<UserInformation> getUserInfo(UUID userId, PgPool client) {
        HashMap<String, ParameterInput> parameters = new HashMap<>();
        parameters.put("ACCOUNT_UUID", new ParameterInput("ACCOUNT_UUID", userId.toString()));
        return databaseController
            .runningQuerySet("USERS", "GET_USER_INFO", parameters, client)
            .onItem()
            .transform(element -> element.get(0).get("main"))
            .onItem()
            .transform(RowSet::iterator)
            .onItem()
            .transform(iterator -> iterator.hasNext() ? UserInformation.from(iterator.next()) : null);
    }

    public Multi<UserInformation> getAllUserInfo(PgPool client) {
        return databaseController
            .runningQuerySet("USERS", "GET_USER_INFO", new HashMap<>(), client)
            .onItem()
            .transform(element -> element.get(0).get("main"))
            .onItem()
            .transformToMulti(set -> Multi.createFrom().iterable(set))
            .onItem()
            .transform(UserInformation::from);
    }
}
