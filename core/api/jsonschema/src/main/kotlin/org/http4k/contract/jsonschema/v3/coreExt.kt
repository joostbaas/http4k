package org.http4k.contract.jsonschema.v3

import org.http4k.contract.jsonschema.IllegalSchemaException
import org.http4k.format.JsonType
import org.http4k.lens.ParamMeta
import org.http4k.lens.ParamMeta.ArrayParam
import org.http4k.lens.ParamMeta.BooleanParam
import org.http4k.lens.ParamMeta.FileParam
import org.http4k.lens.ParamMeta.IntegerParam
import org.http4k.lens.ParamMeta.NullParam
import org.http4k.lens.ParamMeta.NumberParam
import org.http4k.lens.ParamMeta.ObjectParam
import org.http4k.lens.ParamMeta.StringParam

val ParamMeta.value
    get() = when (this) {
        is ArrayParam -> "array"
        ObjectParam -> "object"
        BooleanParam -> "boolean"
        IntegerParam -> "integer"
        FileParam -> "string"
        NumberParam -> "number"
        NullParam -> "null"
        else -> "string"
    }

internal fun JsonType.toParam() = when (this) {
    JsonType.String -> StringParam
    JsonType.Integer -> IntegerParam
    JsonType.Number -> NumberParam
    JsonType.Boolean -> BooleanParam
    JsonType.Array -> ArrayParam(NullParam)
    JsonType.Object -> ObjectParam
    JsonType.Null -> throw IllegalSchemaException("Cannot use a null value in a schema!")
}
