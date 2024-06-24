package com.app.finsassist.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.util.UUID
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
data class User(
    @Id
    @GeneratedValue
    var id: UUID? = null,

    @Column(nullable = false, unique = true)
    var email: String? = null,

    @get:JvmName("getPwd")
    @Column(nullable = false)
    var password: String? = null,

    @Column(nullable = false)
    var firstName: String? = null,

    @Column(nullable = false)
    var lastName: String? = null,

    @ManyToMany
    @JoinTable(name = "users_permissions",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "permission_id")]
    )
    var permissions: Set<Permission> = mutableSetOf(),

    @Column(nullable = false)
    var isDeleted: Boolean = false
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        permissions.toMutableSet()

    override fun getPassword(): String = password.orEmpty()

    override fun getUsername(): String = email.orEmpty()

    override fun isEnabled(): Boolean = isDeleted == false

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String =
        "User(id=$id, email='$email', password='$password', firstName='$firstName', lastName='$lastName')"


}
