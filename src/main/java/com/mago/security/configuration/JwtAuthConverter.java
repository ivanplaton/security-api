package com.mago.security.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Value("${keycloak.principal-attribute}")
    private String PRINCIPAL_ATTRIBUTE;

    @Value("${keycloak.realm-access}")
    private String REALM_ACCESS;

    private final String ROLES = "roles";

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter
            = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities =
                Stream.concat(
                        jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                        extractResourceRoles(jwt).stream()
                ).collect(Collectors.toSet());

        return new JwtAuthenticationToken(
                jwt,
                authorities,
                getPrincipalClaimName(jwt)
        );
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> realmAccess = (Map<String,Object>) jwt.getClaims().get(REALM_ACCESS);
        if(realmAccess == null) {
            return new ArrayList<>();
        }

        List<String > roles = (List<String>) realmAccess.get(ROLES);
        return roles.stream()
                .map(r -> "ROLE_" + r)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private String getPrincipalClaimName(Jwt jwt) {
        return jwt.getClaim(PRINCIPAL_ATTRIBUTE);
    }

}
