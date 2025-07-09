package com.dd3ok.contentflow.domain.automation.controller

import com.dd3ok.contentflow.domain.automation.controller.dto.AutomationCreateRequest
import com.dd3ok.contentflow.domain.automation.controller.dto.AutomationResponse
import com.dd3ok.contentflow.domain.automation.controller.dto.AutomationUpdateRequest
import com.dd3ok.contentflow.domain.automation.repository.AutomationType
import com.dd3ok.contentflow.domain.automation.service.AutomationService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/automations")
class AutomationController(
    private val automationService: AutomationService
) {
    @PostMapping
    fun create(@RequestBody request: AutomationCreateRequest): Mono<AutomationResponse> =
        automationService.create(request)

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String): Mono<AutomationResponse> =
        automationService.getOne(id)

    @GetMapping
    fun getAll(): Flux<AutomationResponse> =
        automationService.getAll()

    @GetMapping("/type/{type}")
    fun getByType(@PathVariable type: AutomationType): Flux<AutomationResponse> =
        automationService.getByType(type)

    @GetMapping("/content/{contentId}")
    fun getBySourceContentId(@PathVariable contentId: String): Flux<AutomationResponse> =
        automationService.getBySourceContentId(contentId)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody request: AutomationUpdateRequest
    ): Mono<AutomationResponse> =
        automationService.update(id, request)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): Mono<Void> =
        automationService.delete(id)
}
