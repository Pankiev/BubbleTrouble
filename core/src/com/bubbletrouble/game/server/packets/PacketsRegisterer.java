package com.bubbletrouble.game.server.packets;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.esotericsoftware.kryo.Kryo;

public class PacketsRegisterer
{
	private final static Class<? extends Annotation> defaultAnnotationType = Registerable.class;

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
		List<Class<?>> sorted = sort(registerableTypes);
		for (Class<?> registerableType : sorted)
			destination = registerType(destination, registerableType);
		destination = registerDefaultClasses(destination);
		return destination;
	}

	private static Kryo registerType(Kryo destination, Class<?> registerableType)
	{
		destination.register(registerableType);
		Class<?> arrayClass = Array.newInstance(registerableType, 0).getClass();
		destination.register(arrayClass);
		return destination;
	}

	private static Kryo registerDefaultClasses(Kryo destination)
	{
		destination = registerType(destination, Integer.class);
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
}
