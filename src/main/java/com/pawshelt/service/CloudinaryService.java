package com.pawshelt.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.util.MultiValueMap;
import org.springframework.http.client.MultipartBodyBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;
    private final WebClient client;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    private static final Set<String> ALLOWED = Set.of("image/jpeg","image/png","image/webp");

    public CloudinaryService(
            Cloudinary cloudinary,
            @Qualifier("cloudinaryClient") WebClient client
    ) {
        this.cloudinary = cloudinary;
        this.client = client;
    }

    public String uploadAnimalPhoto(Long animalId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) throw new IOException("Archivo vacío");
        if (!ALLOWED.contains(file.getContentType())) throw new IOException("Formato no permitido");

        String publicId = "animals/" + animalId + "/" + UUID.randomUUID();
        long timestamp = System.currentTimeMillis() / 1000L;

        // parámetros a firmar (no incluir api_key ni file)
        Map<String, Object> paramsToSign = ObjectUtils.asMap(
                "folder", "animals/" + animalId,
                "public_id", publicId,
                "overwrite", true,
                "timestamp", timestamp
        );

        // firma con el SDK core
        String signature = cloudinary.apiSignRequest(paramsToSign, apiSecret);

        // multipart/form-data
        MultipartBodyBuilder mb = new MultipartBodyBuilder();
        mb.part("file", file.getResource());
        mb.part("api_key", apiKey);
        mb.part("timestamp", String.valueOf(timestamp));
        mb.part("signature", signature);
        mb.part("public_id", publicId);
        mb.part("folder", "animals/" + animalId);
        mb.part("overwrite", "true");

        Map<?,?> res = client.post()
                .uri("/image/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(mb.build()))
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (res == null || !res.containsKey("secure_url")) {
            throw new IOException("Respuesta inesperada de Cloudinary: " + res);
        }
        return (String) res.get("secure_url");
    }
}
