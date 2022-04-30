import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder, StreamsConfig}
import org.apache.kafka.streams.kstream.{KStream, KTable, Printed}

import java.util.Properties

object CurrencyExchange extends App {

  val builder = new StreamsBuilder

  val props = new Properties()
  props.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-table-inner-join")
  props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, ":9092")
  props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass)
  props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass)

  val amounts: KStream[String,String] = builder.stream[String,String]("amounts")
  val rates: KTable[String,String] = builder.table[String,String]("rates")

  amounts.print(Printed.toSysOut[String,String].withLabel("[Amounts]"))

  val solution =amounts.join(rates) {(a:String, rate:String) => (a.toDouble * rate.toDouble).toString}

  solution.to("out")

  val topology = builder.build

  val stream = new KafkaStreams(topology,props)

  stream.start()


}
