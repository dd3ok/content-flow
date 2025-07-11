package com.dd3ok.contentflow.common.config

import com.cloudinary.Cloudinary
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CloudinaryConfig {

    @Value("\${file.upload-url}")
    private lateinit var cloudinaryUrl: String

    @Bean
    fun cloudinary(): Cloudinary {
        return Cloudinary(cloudinaryUrl)
    }
}