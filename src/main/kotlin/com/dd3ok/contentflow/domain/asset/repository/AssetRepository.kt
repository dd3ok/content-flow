package com.dd3ok.contentflow.domain.asset.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface AssetRepository : ReactiveMongoRepository<Asset, String>