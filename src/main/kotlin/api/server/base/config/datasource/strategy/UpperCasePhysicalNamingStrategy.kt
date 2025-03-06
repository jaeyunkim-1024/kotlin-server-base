package api.server.base.config.datasource.strategy

import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.naming.PhysicalNamingStrategy
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment
import java.util.*

class UpperCasePhysicalNamingStrategy : PhysicalNamingStrategy {
    override fun toPhysicalCatalogName(name: Identifier?, context: JdbcEnvironment?): Identifier? {
        return name?.let { convertToUpperCaseSnakeCase(it) }
    }

    override fun toPhysicalSchemaName(name: Identifier?, context: JdbcEnvironment?): Identifier? {
        return name?.let { convertToUpperCaseSnakeCase(it) }
    }

    override fun toPhysicalTableName(name: Identifier?, context: JdbcEnvironment?): Identifier {
        return convertToUpperCaseSnakeCase(name!!)
    }

    override fun toPhysicalSequenceName(name: Identifier?, context: JdbcEnvironment?): Identifier? {
        return name?.let { convertToUpperCaseSnakeCase(it) }
    }

    override fun toPhysicalColumnName(name: Identifier?, context: JdbcEnvironment?): Identifier {
        return convertToUpperCaseSnakeCase(name!!)
    }

    /**
     * CamelCase -> SNAKE_CASE 변환 후 대문자로 변경
     */
    private fun convertToUpperCaseSnakeCase(identifier: Identifier): Identifier {
        val newName = identifier.text
            .replace(Regex("([a-z])([A-Z])"), "$1_$2")
            .replace(Regex("([A-Z])([A-Z][a-z])"), "$1_$2")
            .uppercase(Locale.getDefault())
        return Identifier.toIdentifier(newName)
    }
}