import org.apache.storm.topology.ConfigurableTopology;
import org.apache.storm.topology.TopologyBuilder;

public class TwitTopology extends ConfigurableTopology {

    public static void main(String[] args) {
        ConfigurableTopology.start(new TwitTopology(), args);
    }

    @Override
    protected int run(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("words", new TwitSpout(), 10);
        builder.setBolt("exclaim", new TwitBolt(), 3)
                .shuffleGrouping("words");
        conf.setDebug(true);

        String topologyName = "test";

        conf.setNumWorkers(3);

        if (args != null && args.length > 0) {
            topologyName = args[0];
        }

        return submit(topologyName, conf, builder);

    }
}
