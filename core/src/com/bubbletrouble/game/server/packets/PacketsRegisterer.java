package com.bubbletrouble.game.server.packets;

import java.lang.annotation.Annotation;
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
		String currentPackageName = getCurrentPackageName();
		destination = registerAllAnnotated(destination, defaultAnnotationType, currentPackageName);
		return destination;
	}

	private static String getCurrentPackageName()
	{
		Class<?> thisClassType = new PacketsRegisterer(){}.getClass();
		Package currentPackage = thisClassType.getPackage();
		return currentPackage.getName();
	}

	public static Kryo registerAllAnnotated(Kryo destination, Class<? extends Annotation> annotationType, String sourcePackageName)
	{
		Reflections reflections = new Reflections(sourcePackageName);
		Set<Class<?>> registerableTypes = reflections.getTypesAnnotatedWith(annotationType);
		for (Class<?> registerableType : registerableTypes)
			destination.register(registerableType);
		return destination;
	}



}

