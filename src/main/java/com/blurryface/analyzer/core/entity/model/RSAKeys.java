package com.blurryface.analyzer.core.entity.model;

import com.blurryface.analyzer.core.entity.LongBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "rsa_keys")
public class RSAKeys extends LongBaseEntity {

    @Column(name = "public_key")
    @Lob
    private String publicKey;

    @Column(name = "private_key")
    @Lob
    private String privateKey;
}
