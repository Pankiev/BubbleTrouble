package com.bubbletrouble.game.server.packets;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;

public class PacketsRegisterer
{
	private final static Class<? extends Annotation> defaultAnnotationType = Registerable.class;
	private final static Class<? extends Annotation> defaultAnnotationBase = RegisterableBase.class;

	public static void registerAllAnnotated(Kryo destination)
	{
		destination = registerAllAnnotated(destination, defaultAnnotationType);
	}

	public static Kryo registerAllAnnotated(Kryo destination, Class<? extends Annotation> annotationType)
	{
		String currentPackageName = getUsedPackageName();
		destination = registerAllAnnotated(destination, defaultAnnotationType, currentPackageName);
		return destination;
	}

	private static String getUsedPackageName()
	{
		// Class<?> thisClassType = new PacketsRegisterer(){}.getClass();
		// Package currentPackage = thisClassType.getPackage();
		// return currentPackage.getName();
		return "com.bubbletrouble.game";
	}

	public static Kryo registerAllAnnotated(Kryo destination, Class<? extends Annotation> annotationType,
			String sourcePackageName)
	{
		Reflections reflections = new Reflections(sourcePackageName);
		Set<Class<?>> registerableTypes = reflections.getTypesAnnotatedWith(annotationType);
		destination = registerCollection(destination, registerableTypes);
		Set<Class<?>> registerableBaseTypes = reflections.getTypesAnnotatedWith(defaultAnnotationBase);
		destination = registerSubTypesOf(destination, registerableBaseTypes);
		return destination;
	}

	private static Kryo registerSubTypesOf(Kryo destination, Set<Class<?>> registerableBaseTypes)
	{
		for (Class<?> baseType : registerableBaseTypes)
			destination = registerAllSubtypes(destination, baseType);
		return destination;
	}

	private static Kryo registerCollection(Kryo destination, Set<Class<?>> types)
	{
		List<Class<?>> sorted = sort(types);
		for (Class<?> registerableType : sorted)
			destination = registerType(destination, registerableType);

		return destination;
	}

	private static List<Class<?>> sort(Set<Class<?>> registerableTypes)
	{
		List<Class<?>> sorted = new ArrayList<>(registerableTypes);
		Collections.sort(sorted, new Comparator<Class<?>>()
		{
			public int compare(Class<?> o1, Class<?> o2)
			{
				String name1 = o1.getSimpleName();
				String name2 = o2.getSimpleName();
				return name1.compareTo(name2);
			}
		});
		return sorted;
	}

	private static Kryo registerType(Kryo destination, Class<?> registerableType)
	{
		destination.register(registerableType);
		destination = registerArrayType(destination, registerableType);
		return destination;
	}

	private static Kryo registerArrayType(Kryo destination, Class<?> registerableType)
	{
		Class<?> arrayClass = Array.newInstance(registerableType, 0).getClass();
		destination.register(arrayClass);
		return destination;
	}

	public static <T> Kryo registerAllSubtypes(Kryo destination, Class<T> baseType)
	{
		String packageStr = getUsedPackageName();
		destination = registerAllSubtypes(destination, baseType, packageStr);
		return destination;
	}

	public static <T> Kryo registerAllSubtypes(Kryo destination, Class<T> baseType, String sourcePackageName)
	{
		Reflections reflections = new Reflections(sourcePackageName);
		Set<Class<?>> subtypes = (Set<Class<?>>) (Set<?>) reflections.getSubTypesOf(baseType);
		registerCollection(destination, subtypes);
		return destination;
	}

	public static Kryo registerDefaults(Kryo destination)
	{
		destination = registerType(destination, Vector2.class);
		return destination;
	}

}
