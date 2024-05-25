package com.resired.api.arq;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packages = "com.resired.api", importOptions = {
    ImportOption.DoNotIncludeTests.class,
    ImportOption.DoNotIncludeJars.class
})
public class ArchitectureTest {

    @ArchTest
    public static final ArchRule domainShouldNotHaveAnyDependenciesFromApplication = ArchRuleDefinition
        .noClasses()
        .that().resideInAPackage("..domain..")
        .should().dependOnClassesThat()
        .resideInAPackage("..application..");
    @ArchTest
    public static final ArchRule domainShouldNotHaveAnyDependenciesFromInfra = ArchRuleDefinition
        .noClasses()
        .that().resideInAPackage("..domain..")
        .should().dependOnClassesThat()
        .resideInAPackage("..infraestructure..");

    @ArchTest
    public static final ArchRule applicationShouldNotHaveAnyDependenciesFromInfra = ArchRuleDefinition
        .noClasses()
        .that().resideInAPackage("..application..")
        .should().dependOnClassesThat()
        .resideInAPackage("..infraestructure..");
}
