package com.accenture.service.dto;

import java.time.LocalDateTime;


public record ErreurResponse(LocalDateTime temporalite, String type, String message) {}
