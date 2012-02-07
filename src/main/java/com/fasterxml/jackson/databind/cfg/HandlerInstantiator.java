package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;

/**
 * Helper class used for handling details of creating handler instances (things
 * like {@link JsonSerializer}s, {@link JsonDeserializer}s, various type
 * handlers) of specific types. Actual handler type has been resolved at this
 * point, so instantiator is strictly responsible for providing a configured
 * instance by constructing and configuring a new instance, or possibly by
 * recycling a shared instance. One use case is that of allowing
 * dependency injection, which would otherwise be difficult to do.
 *<p>
 * Custom instances are allowed to return null to indicate that caller should
 * use the default instantiation handling (which just means calling no-argument
 * constructor via reflection).
 *<p>
 * Care has to be taken to ensure that if instance returned is shared, it will
 * be thread-safe; caller will not synchronize access to returned instances.
 */
public abstract class HandlerInstantiator
{
    /*
    /**********************************************************
    /* Public API
    /**********************************************************
     */

    /**
     * Method called to get an instance of deserializer of specified type.
     * 
     * @param config Deserialization configuration in effect
     * @param annotated Element (Class, Method, Field, constructor parameter) that
     *    had annotation defining class of deserializer to construct (to allow
     *    implementation use information from other annotations)
     * @param deserClass Class of deserializer instance to return
     * 
     * @return Deserializer instance to use
     */
    public abstract JsonDeserializer<?> deserializerInstance(DeserializationConfig config,
            Annotated annotated, Class<?> deserClass);

    /**
     * Method called to get an instance of key deserializer of specified type.
     * 
     * @param config Deserialization configuration in effect
     * @param annotated Element (Class, Method, Field, constructor parameter) that
     *    had annotation defining class of key deserializer to construct (to allow
     *    implementation use information from other annotations)
     * @param keyDeserClass Class of key deserializer instance to return
     * 
     * @return Key deserializer instance to use
     */
    public abstract KeyDeserializer keyDeserializerInstance(DeserializationConfig config,
            Annotated annotated, Class<?> keyDeserClass);
    
    /**
     * Method called to get an instance of serializer of specified type.
     * 
     * @param config Serialization configuration in effect
     * @param annotated Element (Class, Method, Field) that
     *    had annotation defining class of serializer to construct (to allow
     *    implementation use information from other annotations)
     * @param serClass Class of serializer instance to return
     * 
     * @return Serializer instance to use
     */
    public abstract JsonSerializer<?> serializerInstance(SerializationConfig config,
            Annotated annotated, Class<?> serClass);

    /**
     * Method called to get an instance of TypeResolverBuilder of specified type.
     * 
     * @param config Mapper configuration in effect (either SerializationConfig or
     *   DeserializationConfig, depending on when instance is being constructed)
     * @param annotated annotated Element (Class, Method, Field) that
     *    had annotation defining class of builder to construct (to allow
     *    implementation use information from other annotations)
     * @param builderClass Class of builder instance to return
     * 
     * @return TypeResolverBuilder instance to use
     */
    public abstract TypeResolverBuilder<?> typeResolverBuilderInstance(MapperConfig<?> config,
            Annotated annotated, Class<?> builderClass);

    /**
     * Method called to get an instance of TypeIdResolver of specified type.
     * 
     * @param config Mapper configuration in effect (either SerializationConfig or
     *   DeserializationConfig, depending on when instance is being constructed)
     * @param annotated annotated Element (Class, Method, Field) that
     *    had annotation defining class of resolver to construct (to allow
     *    implementation use information from other annotations)
     * @param resolverClass Class of resolver instance to return
     * 
     * @return TypeResolverBuilder instance to use
     */
    public abstract TypeIdResolver typeIdResolverInstance(MapperConfig<?> config,
            Annotated annotated, Class<?> resolverClass);

    /**
     * Method called to construct an instance of ValueInstantiator of specified type.
     */
    public ValueInstantiator valueInstantiatorInstance(MapperConfig<?> config,
            Annotated annotated, Class<?> resolverClass) {
        return null;
    }

    
    /**
     * Method called to construct a ObjectIdHandler instance of specified type.
     * 
     * @since 2.0
     */
    public ObjectIdGenerator<?> objectIdGeneratorInstance(MapperConfig<?> config,
            Annotated annotated, Class<?> implClass) {
        return null;
    }
}
