package ru.iedt.authorization.models;

import io.vertx.mutiny.sqlclient.Row;
import java.util.Objects;
import java.util.UUID;

public class AppInformation {

    private final UUID app_id;
    private final String app_name;
    private final String app_secret;
    private final String app_token_secret;
    private final String app_redirect_url;
    private final String app_image;

    public AppInformation(UUID app_id, String app_name, String app_secret, String app_token_secret, String app_redirect_url, String app_image) {
        this.app_id = app_id;
        this.app_name = app_name;
        this.app_secret = app_secret;
        this.app_token_secret = app_token_secret;
        this.app_redirect_url = app_redirect_url;
        this.app_image = app_image;
    }

    public UUID getAppId() {
        return app_id;
    }

    public String getAppName() {
        return app_name;
    }

    public String getAppSecret() {
        return app_secret;
    }

    public String getAppTokenSecret() {
        return app_token_secret;
    }

    public String getRedirectUrl() {
        return app_redirect_url;
    }

    public String getAppImage() {
        return app_image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppInformation that = (AppInformation) o;

        if (!Objects.equals(app_id, that.app_id)) return false;
        if (!Objects.equals(app_name, that.app_name)) return false;
        if (!Objects.equals(app_secret, that.app_secret)) return false;
        if (!Objects.equals(app_token_secret, that.app_token_secret)) return false;
        if (!Objects.equals(app_redirect_url, that.app_redirect_url)) return false;
        return Objects.equals(app_image, that.app_image);
    }

    @Override
    public int hashCode() {
        int result = app_id != null ? app_id.hashCode() : 0;
        result = 31 * result + (app_name != null ? app_name.hashCode() : 0);
        result = 31 * result + (app_secret != null ? app_secret.hashCode() : 0);
        result = 31 * result + (app_token_secret != null ? app_token_secret.hashCode() : 0);
        result = 31 * result + (app_redirect_url != null ? app_redirect_url.hashCode() : 0);
        result = 31 * result + (app_image != null ? app_image.hashCode() : 0);
        return result;
    }

    public static AppInformation from(Row row) {
        return new AppInformation(row.getUUID("app_id"), row.getString("app_name"), row.getString("app_secret"), row.getString("app_token_secret"), row.getString("app_redirect_url"), row.getString("app_image"));
    }
}
