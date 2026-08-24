#!/bin/sh

# Gradle wrapper script

set -e

if [ -z "$JAVA_HOME" ]; then
    echo "JAVA_HOME is not set" >&2
    exit 1
fi

exec "$JAVA_HOME/bin/java" -cp "gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
