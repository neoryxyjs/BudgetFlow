package com.example.budgetflow.api.mapper

import com.example.budgetflow.api.dto.ExpenseDto
import com.example.budgetflow.api.dto.UserDto
import com.example.budgetflow.model.Expense
import com.example.budgetflow.model.User
import com.google.firebase.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object ModelMapper {
    // Usar ISO 8601 con zona horaria para mejor compatibilidad con el backend
    // Formato: 2025-11-30T16:23:10.616Z
    private val dateFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    
    // Formato alternativo sin milisegundos (por si el backend no los acepta)
    private val dateFormatterSimple = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    
    // Expense Mappers
    fun Expense.toDto(): ExpenseDto {
        // Formato simple sin zona horaria para mejor compatibilidad con Spring Boot
        // Spring Boot espera: yyyy-MM-dd'T'HH:mm:ss o yyyy-MM-dd'T'HH:mm:ss.SSS
        val dateStr = try {
            val instant = Instant.ofEpochSecond(date.seconds, date.nanoseconds.toLong())
            val localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
            // Formato: yyyy-MM-dd'T'HH:mm:ss (sin zona horaria, Spring Boot lo maneja mejor)
            localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        } catch (e: Exception) {
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        }
        
        return ExpenseDto(
            id = if (id.isNotEmpty()) id.toLongOrNull() else null,
            userId = userId,
            amount = amount,
            category = category,
            description = description.ifEmpty { "" }, // Enviar string vacío en lugar de null
            date = dateStr,
            createdAt = null // No enviar createdAt en creación - el backend lo genera automáticamente
        )
    }
    
    fun ExpenseDto.toModel(): Expense {
        val date = try {
            val zonedDateTime = java.time.ZonedDateTime.parse(this.date, dateFormatter)
            val instant = zonedDateTime.toInstant()
            Timestamp(instant.epochSecond, instant.nano)
        } catch (e: Exception) {
            Timestamp.now()
        }
        
        val createdAt = try {
            this.createdAt?.let {
                val zonedDateTime = java.time.ZonedDateTime.parse(it, dateFormatter)
                val instant = zonedDateTime.toInstant()
                Timestamp(instant.epochSecond, instant.nano)
            } ?: Timestamp.now()
        } catch (e: Exception) {
            Timestamp.now()
        }
        
        return Expense(
            id = id?.toString() ?: "",
            userId = userId,
            amount = amount,
            category = category,
            description = description ?: "",
            date = date,
            createdAt = createdAt
        )
    }
    
    // User Mappers
    fun User.toDto(): UserDto {
        val createdAtStr = try {
            val instant = Instant.ofEpochSecond(createdAt.seconds, createdAt.nanoseconds.toLong())
            instant.atZone(ZoneId.systemDefault())
                .format(dateFormatter)
        } catch (e: Exception) {
            Instant.now().atZone(ZoneId.systemDefault()).format(dateFormatter)
        }
        
        return UserDto(
            id = id,
            name = name,
            email = email,
            monthlyBudget = monthlyBudget,
            profileImageUrl = profileImageUrl.ifEmpty { null },
            createdAt = createdAtStr
        )
    }
    
    fun UserDto.toModel(): User {
        val createdAt = try {
            this.createdAt?.let {
                val zonedDateTime = java.time.ZonedDateTime.parse(it, dateFormatter)
                val instant = zonedDateTime.toInstant()
                Timestamp(instant.epochSecond, instant.nano)
            } ?: Timestamp.now()
        } catch (e: Exception) {
            Timestamp.now()
        }
        
        android.util.Log.d("ModelMapper", "Convirtiendo UserDto a User - monthlyBudget: ${this.monthlyBudget}")
        
        return User(
            id = id,
            name = name,
            email = email,
            monthlyBudget = monthlyBudget,
            profileImageUrl = profileImageUrl ?: "",
            createdAt = createdAt
        ).also {
            android.util.Log.d("ModelMapper", "User creado - monthlyBudget: ${it.monthlyBudget}")
        }
    }
}

