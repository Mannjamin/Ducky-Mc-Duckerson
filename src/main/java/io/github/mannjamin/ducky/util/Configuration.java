package io.github.mannjamin.ducky.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * An instance of the current configuration
 *
 * @author Matthew Struble
 */
public class Configuration {
    public final String token;
    public final String admin;
    public final byte debugLevel; // TODO this should be an enum

    public final String databaseName;

    public final InetAddress socketHost;
    public final short socketPort;

    /**
     * Creates a new configuration with the given root element
     *
     * @param root The root object
     *
     * @throws UndefinedTokenException If the bot token is undefined
     * @throws UnknownHostException    If the hostname could not be resolved
     */
    Configuration(JsonObject root) throws UndefinedTokenException, UnknownHostException {
        final JsonElement tokenE = root.get("token");
        if (tokenE.isJsonNull()) throw new UndefinedTokenException();
        else token = tokenE.getAsString();

        final JsonElement adminE = root.get("admin");
        admin = adminE.isJsonNull() ? null : adminE.getAsString();

        debugLevel = root.get("debugLevel").getAsByte();

        JsonObject db = root.getAsJsonObject("db");
        databaseName = db.get("name").getAsString();

        JsonObject socket = root.getAsJsonObject("socket");
        socketHost = InetAddress.getByName(socket.get("host").getAsString());
        socketPort = socket.get("port").getAsShort();
    }
}
