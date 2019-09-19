package socialnetwork;

import java.io.BufferedInputStream;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orientechnologies.common.util.OCallable;
import com.orientechnologies.orient.server.OServer;
import com.orientechnologies.orient.server.OServerMain;
import com.orientechnologies.orient.server.config.OServerCommandConfiguration;
import com.orientechnologies.orient.server.config.OServerConfiguration;
import com.orientechnologies.orient.server.config.OServerEntryConfiguration;
import com.orientechnologies.orient.server.config.OServerHandlerConfiguration;
import com.orientechnologies.orient.server.config.OServerNetworkConfiguration;
import com.orientechnologies.orient.server.config.OServerNetworkListenerConfiguration;
import com.orientechnologies.orient.server.config.OServerNetworkProtocolConfiguration;
import com.orientechnologies.orient.server.config.OServerParameterConfiguration;
import com.orientechnologies.orient.server.config.OServerStorageConfiguration;
import com.orientechnologies.orient.server.config.OServerUserConfiguration;
import com.orientechnologies.orient.server.network.OServerNetworkListener;
import com.orientechnologies.orient.server.network.protocol.http.ONetworkProtocolHttpAbstract;
import com.orientechnologies.orient.server.network.protocol.http.command.get.OServerCommandGetStaticContent;

public class OrientDBServerManager {

  private final static Logger LOG = LoggerFactory.getLogger(OrientDBServerManager.class);

  private static OServer server;

	public static void startOrientDBServer(String nodeName, String databaseName) {
		LOG.debug("Starting OrientDB server");

    String orientdbHome = new File("").getAbsolutePath();
    System.setProperty("ORIENTDB_HOME", orientdbHome);
    
    try {
    	server = OServerMain.create(true);
			server.startup(createServerConfiguration(nodeName, databaseName));
            
	    server.activate();
			LOG.debug("OrientDB server started");
		} catch (Exception e) {
			throw new RuntimeException("Could not start OrientDB server", e);
		}

    final OServerNetworkListener httpListener = server.getListenerByProtocol(ONetworkProtocolHttpAbstract.class);
    if (httpListener != null) {
        final OServerCommandGetStaticContent command = (OServerCommandGetStaticContent) httpListener.getCommand(OServerCommandGetStaticContent.class);

        final String studioPath = "/studio/";
        command.registerVirtualFolder("studio", new OCallable<Object, String>() {
            @Override
            public Object call(final String iArgument) {
                final String fileName = studioPath + ((null != iArgument && !"".equals(iArgument)) ? iArgument : "index.htm");
                final URL url = getClass().getResource(fileName);
                if (url != null) {
                    final OServerCommandGetStaticContent.OStaticContent content =
                            new OServerCommandGetStaticContent.OStaticContent();
                    content.is = new BufferedInputStream(getClass().getResourceAsStream(fileName));
                    content.contentSize = -1;
                    content.type = OServerCommandGetStaticContent.getContentType(url.getFile());
                    return content;
                }
                return null;
            }
        });
    }
	}
	
	public static void shutdownOrientDBServer() {
		if (null != server) {
			LOG.debug("Stopping OrientDB server");
			server.shutdown();
			LOG.debug("OrientDB server stopped");
		}
	}
	
	private static OServerConfiguration createServerConfiguration(String nodeName, String databaseName) {
    OServerConfiguration cfg = new OServerConfiguration();

    //DB server config
    cfg.handlers = new ArrayList<OServerHandlerConfiguration>();
    //Distributed plugin config
//    addDistributedCfg(cfg, nodeName);
    
    //Network config
    cfg.network = new OServerNetworkConfiguration();
    cfg.network.protocols = new ArrayList<OServerNetworkProtocolConfiguration>();
    cfg.network.listeners = new ArrayList<>();
    addBinaryListenerCfg(cfg);
    addHttpListenerCfg(cfg);
    
    //Storage config
    OServerStorageConfiguration storageCfg = new OServerStorageConfiguration();
    storageCfg.path = "plocal:./databases/" + databaseName;
    storageCfg.name = databaseName;
    storageCfg.loadOnStartup = true;
    cfg.storages = new OServerStorageConfiguration[]{storageCfg};
    //User config
    OServerUserConfiguration userCfg = new OServerUserConfiguration("root", "admin", "*");
    cfg.users = new OServerUserConfiguration[]{userCfg};
    
    return cfg;
	}
	
	private static void addDistributedCfg(OServerConfiguration cfg, String nodeName) {
	    OServerHandlerConfiguration hazelcastHandlerConfig = new OServerHandlerConfiguration();
	    hazelcastHandlerConfig.clazz = "com.orientechnologies.orient.server.hazelcast.OHazelcastPlugin";
	    hazelcastHandlerConfig.parameters = new OServerParameterConfiguration[6];
	    hazelcastHandlerConfig.parameters[0] = new OServerParameterConfiguration("nodeName", nodeName);
	    hazelcastHandlerConfig.parameters[1] = new OServerParameterConfiguration("enabled", "true");
	    hazelcastHandlerConfig.parameters[2] = new OServerParameterConfiguration("configuration.db.default", "/tmp/default-distributed-db-config.json");
	    hazelcastHandlerConfig.parameters[3] = new OServerParameterConfiguration("configuration.hazelcast", "/tmp/hazelcast.xml");
	    hazelcastHandlerConfig.parameters[4] = new OServerParameterConfiguration("conflict.resolver.impl", "com.orientechnologies.orient.server.distributed.conflict.ODefaultReplicationConflictResolver");
	    hazelcastHandlerConfig.parameters[5] = new OServerParameterConfiguration("sharding.strategy.round-robin", "com.orientechnologies.orient.server.hazelcast.sharding.strategy.ORoundRobinPartitioninStrategy");
	    cfg.handlers.add(hazelcastHandlerConfig);
	}
	
	private static void addBinaryListenerCfg(OServerConfiguration cfg) {
	    cfg.network.protocols.add(new OServerNetworkProtocolConfiguration("binary", "com.orientechnologies.orient.server.network.protocol.binary.ONetworkProtocolBinary"));
	    OServerNetworkListenerConfiguration binaryListenerCfg = new OServerNetworkListenerConfiguration();
	    binaryListenerCfg.ipAddress = "0.0.0.0";
	    binaryListenerCfg.portRange = "2424-2430";
	    binaryListenerCfg.protocol = "binary";
	    
	    cfg.network.listeners.add(binaryListenerCfg);
	}
	
	private static void addHttpListenerCfg(OServerConfiguration cfg) {
	    cfg.network.protocols.add(new OServerNetworkProtocolConfiguration("http", "com.orientechnologies.orient.server.network.protocol.http.ONetworkProtocolHttpDb"));
	
	    OServerNetworkListenerConfiguration httpListenerCfg = new OServerNetworkListenerConfiguration();
	    httpListenerCfg.ipAddress = "0.0.0.0";
	    httpListenerCfg.portRange = "2480-24390";
	    httpListenerCfg.protocol = "http";
	
	    OServerParameterConfiguration httpParam1 = new OServerParameterConfiguration("network.http.charset", "utf-8");
	    OServerParameterConfiguration httpParam2 = new OServerParameterConfiguration("network.http.jsonResponseError", "true");
	    OServerParameterConfiguration[] httpParams = {httpParam1, httpParam2};
	    httpListenerCfg.parameters = httpParams;
	
	    OServerCommandConfiguration httpCommandConfig = new OServerCommandConfiguration();
	    httpCommandConfig.pattern = "GET|www GET|studio/ GET|studio GET| GET|*.htm GET|*.html GET|*.xml GET|*.jpeg GET|*.jpg GET|*.png GET|*.gif GET|*.js GET|*.css GET|*.swf GET|*.ico GET|*.txt GET|*.otf GET|*.pjs GET|*.svg GET|*.json GET|*.woff GET|*.woff2 GET|*.ttf GET|*.svgz";
	    httpCommandConfig.implementation = "com.orientechnologies.orient.server.network.protocol.http.command.get.OServerCommandGetStaticContent";
	
	    List<OServerEntryConfiguration> httpCommandParams = new ArrayList<OServerEntryConfiguration>();
	    httpCommandParams.add(new OServerEntryConfiguration("http.cache:*.htm *.html", "Cache-Control: no-cache, no-store, max-age=0, must-revalidate\\r\\nPragma: no-cache"));
	    httpCommandParams.add(new OServerEntryConfiguration("http.cache:default", "Cache-Control: max-age=120"));
	    httpCommandConfig.parameters = httpCommandParams.toArray(new OServerEntryConfiguration[0]);
	
	    OServerCommandConfiguration[] httpCommandConfigs = { httpCommandConfig };
	    httpListenerCfg.commands = httpCommandConfigs;
	
	    cfg.network.listeners.add(httpListenerCfg);
	}

}
