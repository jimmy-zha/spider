package com.ucloud.umq.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by alpha on 8/5/16.
 */
public class ServiceConfig {
  private static String SETTING_FILE = System.getProperty("user.home") + System.getProperty("file.separator") + "ucloud_umq.properties";

  private static Properties properties = new Properties();

  private static final Log log = LogFactory.getLog(ServiceConfig.class);

  static {
    load();
  }

  public static void load() {
    load(SETTING_FILE);
  }

  public static void load(String configFile) {
    InputStream is = null;
    try {
      is = new FileInputStream(configFile);
      properties.load(is);
    } catch (FileNotFoundException e) {
      log.warn("The setting file " + configFile + " was not found.");
    } catch (java.io.IOException e) {
      log.warn("Load properties from file " + configFile + " failed");
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {

        }
      }
    }
  }

  public static String getProperty(String key) {
    return properties.getProperty(key);
  }

  public static String getPublicKey() {
    return properties.getProperty("ucloud.PublicKey");
  }

  public static String getPrivateKey() {
    return properties.getProperty("ucloud.PrivateKey");
  }

  public static String getProjectId() {
    return properties.getProperty("ucloud.ProjectId");
  }

  public static String getApiUri() {
    return properties.getProperty("ucloud.ApiUri");
  }

  public static String getRegion() {
    return properties.getProperty("ucloud.Region");
  }

  public static String getHost() {
    return properties.getProperty("ucloud.Host");
  }

  public static String getAccount() {
    return properties.getProperty("ucloud.Account");
  }

  public static String getRole() {
    return properties.getProperty("umq.Role");
  }

  public static String getHttpUrl() {
    return properties.getProperty("umq.HttpUrl");
  }

  public static String getWsUrl() {
    return properties.getProperty("umq.WsUrl");
  }

  public static String getConsumerId() {
    return properties.getProperty("umq.ConsumerId");
  }

  public static String getConsumerToken() {
    return properties.getProperty("umq.ConsumerToken");
  }

  public static int getOrganizationId() {
    String orgId = properties.getProperty("ucloud.OrganizationId");
    return Integer.parseInt(orgId);
  }

  public static String getRpcHost() {
    return properties.getProperty("rpc.host");
  }

  public static int getRpcPort() {
    String port = properties.getProperty("rpc.port");
    return Integer.parseInt(port);
  }

  public static int getRmqConnections() {
    String value = properties.getProperty("rmq.connections");
    return Integer.parseInt(value);
  }

  public static String getRmqUri() {
    return properties.getProperty("rmq.uri");
  }

  public static String getRmqHost() {
    return properties.getProperty("rmq.host");
  }

  public static String getRmqVhost() {
    return properties.getProperty("rmq.vhost");
  }

  public static int getRmqPort() {
    String value = properties.getProperty("rmq.port");
    return Integer.parseInt(value);
  }

  public static String getRmqUsername() {
    return properties.getProperty("rmq.username");
  }

  public static String getRmqPassword() {
    return properties.getProperty("rmq.password");
  }

  public static String getSentUrl() {
    return properties.getProperty("sent.url");
  }

  public static String getBaiduMobilePath() {
    return properties.getProperty("baidu.mobile.path");
  }

  public static String getJuheMobilePath() {
    return properties.getProperty("juhe.mobile.path");
  }

  public static String getMobileExchange() {
    return properties.getProperty("rmq.mobile.exchange");
  }

  public static String getGPSExchange() {
    return properties.getProperty("rmq.gps.exchange");
  }

  public static String getJdExchange() {
    return properties.getProperty("rmq.jd.exchange");
  }

  public static String getBankCardExchange() {
    return properties.getProperty("rmq.bankcard.exchange");
  }

  public static String getMerchantInfoExchange() {
    return properties.getProperty("rmq.merchantinfo.exchange");
  }

  public static String getBitautoExchange() {
    return properties.getProperty("rmq.bitauto.exchange");
  }

  public static String getQqNumberExchange() {
    return properties.getProperty("rmq.qqnumber.exchange");
  }

  public static String getQqNumberRoutingKey() {
    return properties.getProperty("rmq.qqnumber.routingKey");
  }

  public static String getBitautoRoutingKey() {
    return properties.getProperty("rmq.bitauto.routingKey");
  }

  public static String getMobilerRoutingKey() {
    return properties.getProperty("rmq.mobile.routingKey");
  }

  public static String getBankcardRoutingKey() {
    return properties.getProperty("rmq.bankcard.routingKey");
  }

  public static String getMerchantInfoRoutingKey() {
    return properties.getProperty("rmq.merchantinfo.routingKey");
  }

  public static String getGPSRoutingKey() {
    return properties.getProperty("rmq.gps.routingKey");
  }

  public static Properties getProperties() {
    return properties;
  }

  public static String getSogouMobilePath() {
    return properties.getProperty("sogou.mobile.path");
  }

  public static String getGuabuBankCardPath() {
    return properties.getProperty("guabu.bank.card.path");
  }

  public static String getHuoChePiaoBankCardPath() {
    return properties.getProperty("hcp.bank.card.path");
  }

  public static String getCha67BankCardPath() {
    return properties.getProperty("cha67.bank.card.path");
  }

  public static String getYinHangKaBankCardPath() {
    return properties.getProperty("yhk388.bank.card.path");
  }

  public static String getChaYHKBankCardPath() {
    return properties.getProperty("cha.yhk.bank.card.path");
  }

  public static String getLBSAMapKey() {
    return properties.getProperty("lbs.amap.key");
  }

  public static String getLBSAMapGeoPath() {
    return properties.getProperty("lbs.amap.geo.path");
  }

  public static String getLBSAMapReGeoPath() {
    return properties.getProperty("lbs.amap.regeo.path");
  }

  public static String getIP138Path() {
    return properties.getProperty("ip138.mobile.path");
  }

  public static String getHuoche114Path() {
    return properties.getProperty("huoche114.mobile.path");
  }

  public static String getGuishuShowjiPath() {
    return properties.getProperty("guishushowji.mobile.path");
  }

  public static String getTianyanchaPath() {
    return properties.getProperty("tianyancha.path");
  }

  public static String getBitautoPath() {
    return properties.getProperty("bitauto.path");
  }

  public static void main(String[] args) {
    System.out.println(getHuoche114Path());
  }

  public static String getLBSBaiduGeoPath() {
    return properties.getProperty("lbs.baidu.geo.path");
  }

  public static String getLBSBaiduReGeoPath() {
    return properties.getProperty("lbs.baidu.regeo.path");
  }

  public static String getLBSBaiduKey() {
    return properties.getProperty("lbs.baidu.key");
  }

  public static String getLbsExchange() {
    return properties.getProperty("rmq.lbs.exchange");
  }

  public static String getLbsRoutingKey() {
    return properties.getProperty("rmq.lbs.routingKey");
  }

  public static String isStartTianYanChaOff() {
    return properties.getProperty("tianyancha.off");
  }

  public static String getSaiGeGPSPath() {
    return properties.getProperty("saige.gps.path");
  }

  // 获取身份证黑名单入口
  public static String getBlackIdCardPath() {
    return properties.getProperty("black.idCard.path");
  }

  // 身份证黑名单
  public static String getBlackIdCardExchange() {
    return properties.getProperty("rmq.black.list.exchanges");
  }

  // 身份证黑名单
  public static String getBlackIdCardRoutingKey() {
    return properties.getProperty("rmq.gxsky.routingKey");
  }

  // QQZone
  public static String getQQZoneIndex() {
    return properties.getProperty("qqzone.index.path");
  }
}
