package org.bogies.gateway.config;

import java.util.Date;

import org.bogies.common.entity.JWTPayloadEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
@ConfigurationProperties(prefix = "jwt")
/**
 * @ClassName: JWTConfig
 * @Description: TODO
 * @author: jerry
 * @date: 2019年01月29日 上午10:29:38
 */
public class JWTConfig {
	private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * 加密秘钥
     */
    private String secret;
    /**
     * 有效时间
     */
    private long expire;
    /**
     * 用户凭证
     */
    private String header;

    public String getSecret() {
        return secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
    public long getExpire() {
        return expire;
    }
    public void setExpire(long expire) {
        this.expire = expire;
    }
    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }
    /**
     * 生成Token签名
     * @param payload 包含用户id和用户名
     * @return 签名字符串
     */
    public String generateToken(JWTPayloadEntity payload) {       
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setHeaderParam("typ", "JWT");
        jwtBuilder.setPayload(JSON.toJSONString(payload));
        jwtBuilder.signWith(SignatureAlgorithm.HS256, getSecret());
        
        return jwtBuilder.compact();
    }
    /**
     * 获取签名信息
     * @param token
     * @author geYang
     * @date 2018-05-18 16:47
     */
    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
        	LOGGER.debug("验证token错误 ", e.getCause().getMessage());
            return null;
        }
    }
    /**
     * 判断Token是否过期
     * @param expiration
     * @return true 过期
     * @author geYang
     * @date 2018-05-18 16:41
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
