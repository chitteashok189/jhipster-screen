package org.jhipster.blog.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, org.jhipster.blog.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, org.jhipster.blog.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, org.jhipster.blog.domain.User.class.getName());
            createCache(cm, org.jhipster.blog.domain.Authority.class.getName());
            createCache(cm, org.jhipster.blog.domain.User.class.getName() + ".authorities");
            createCache(cm, org.jhipster.blog.domain.Party.class.getName());
            createCache(cm, org.jhipster.blog.domain.Party.class.getName() + ".partyAttributes");
            createCache(cm, org.jhipster.blog.domain.Party.class.getName() + ".partyClassifications");
            createCache(cm, org.jhipster.blog.domain.Party.class.getName() + ".partyRoles");
            createCache(cm, org.jhipster.blog.domain.Party.class.getName() + ".partyNotes");
            createCache(cm, org.jhipster.blog.domain.Party.class.getName() + ".people");
            createCache(cm, org.jhipster.blog.domain.Party.class.getName() + ".applicationUsers");
            createCache(cm, org.jhipster.blog.domain.Party.class.getName() + ".farms");
            createCache(cm, org.jhipster.blog.domain.PartyGroup.class.getName());
            createCache(cm, org.jhipster.blog.domain.PartyGroup.class.getName() + ".parties");
            createCache(cm, org.jhipster.blog.domain.PartyType.class.getName());
            createCache(cm, org.jhipster.blog.domain.PartyType.class.getName() + ".parties");
            createCache(cm, org.jhipster.blog.domain.PartyTypeAttribute.class.getName());
            createCache(cm, org.jhipster.blog.domain.Ennumeration.class.getName());
            createCache(cm, org.jhipster.blog.domain.EnnumerationType.class.getName());
            createCache(cm, org.jhipster.blog.domain.PartyAttribute.class.getName());
            createCache(cm, org.jhipster.blog.domain.PartyClassification.class.getName());
            createCache(cm, org.jhipster.blog.domain.PartyRole.class.getName());
            createCache(cm, org.jhipster.blog.domain.PartyRole.class.getName() + ".parties");
            createCache(cm, org.jhipster.blog.domain.PartyRole.class.getName() + ".roleTypes");
            createCache(cm, org.jhipster.blog.domain.PartyRelationship.class.getName());
            createCache(cm, org.jhipster.blog.domain.PartyRelationship.class.getName() + ".partyRelationshipTypes");
            createCache(cm, org.jhipster.blog.domain.PartyRelationshipType.class.getName());
            createCache(cm, org.jhipster.blog.domain.PartyRelationshipType.class.getName() + ".partyRelationships");
            createCache(cm, org.jhipster.blog.domain.RoleType.class.getName());
            createCache(cm, org.jhipster.blog.domain.RoleType.class.getName() + ".partyRoles");
            createCache(cm, org.jhipster.blog.domain.RoleType.class.getName() + ".roleTypeAttributes");
            createCache(cm, org.jhipster.blog.domain.RoleTypeAttribute.class.getName());
            createCache(cm, org.jhipster.blog.domain.RoleTypeAttribute.class.getName() + ".roleTypes");
            createCache(cm, org.jhipster.blog.domain.PartyNote.class.getName());
            createCache(cm, org.jhipster.blog.domain.PartyStatus.class.getName());
            createCache(cm, org.jhipster.blog.domain.PartyStatusType.class.getName());
            createCache(cm, org.jhipster.blog.domain.PartyStatusItem.class.getName());
            createCache(cm, org.jhipster.blog.domain.Person.class.getName());
            createCache(cm, org.jhipster.blog.domain.Person.class.getName() + ".parties");
            createCache(cm, org.jhipster.blog.domain.ApplicationUser.class.getName());
            createCache(cm, org.jhipster.blog.domain.ApplicationUser.class.getName() + ".parties");
            createCache(cm, org.jhipster.blog.domain.SecurityGroup.class.getName());
            createCache(cm, org.jhipster.blog.domain.SecurityGroup.class.getName() + ".partyRelationships");
            createCache(cm, org.jhipster.blog.domain.SecurityGroup.class.getName() + ".applicationUserSecurityGroups");
            createCache(cm, org.jhipster.blog.domain.SecurityGroup.class.getName() + ".securityGroupPermissions");
            createCache(cm, org.jhipster.blog.domain.SecurityPermission.class.getName());
            createCache(cm, org.jhipster.blog.domain.SecurityPermission.class.getName() + ".securityGroupPermissions");
            createCache(cm, org.jhipster.blog.domain.ApplicationUserSecurityGroup.class.getName());
            createCache(cm, org.jhipster.blog.domain.ApplicationUserSecurityGroup.class.getName() + ".securityGroups");
            createCache(cm, org.jhipster.blog.domain.SecurityGroupPermission.class.getName());
            createCache(cm, org.jhipster.blog.domain.SecurityGroupPermission.class.getName() + ".securityGroups");
            createCache(cm, org.jhipster.blog.domain.SecurityGroupPermission.class.getName() + ".securityPermissions");
            createCache(cm, org.jhipster.blog.domain.Farm.class.getName());
            createCache(cm, org.jhipster.blog.domain.Farm.class.getName() + ".plantFactories");
            createCache(cm, org.jhipster.blog.domain.PlantFactory.class.getName());
            createCache(cm, org.jhipster.blog.domain.PlantFactory.class.getName() + ".zones");
            createCache(cm, org.jhipster.blog.domain.PlantFactory.class.getName() + ".devices");
            createCache(cm, org.jhipster.blog.domain.PlantFactory.class.getName() + ".climates");
            createCache(cm, org.jhipster.blog.domain.PlantFactory.class.getName() + ".irrigations");
            createCache(cm, org.jhipster.blog.domain.PlantFactory.class.getName() + ".dosings");
            createCache(cm, org.jhipster.blog.domain.PlantFactory.class.getName() + ".lights");
            createCache(cm, org.jhipster.blog.domain.PlantFactory.class.getName() + ".crops");
            createCache(cm, org.jhipster.blog.domain.PlantFactory.class.getName() + ".calendars");
            createCache(cm, org.jhipster.blog.domain.PlantFactory.class.getName() + ".scoutings");
            createCache(cm, org.jhipster.blog.domain.PlantFactory.class.getName() + ".pests");
            createCache(cm, org.jhipster.blog.domain.PlantFactory.class.getName() + ".diseases");
            createCache(cm, org.jhipster.blog.domain.Zone.class.getName());
            createCache(cm, org.jhipster.blog.domain.Zone.class.getName() + ".calendars");
            createCache(cm, org.jhipster.blog.domain.Zone.class.getName() + ".activities");
            createCache(cm, org.jhipster.blog.domain.GrowBed.class.getName());
            createCache(cm, org.jhipster.blog.domain.GrowBed.class.getName() + ".zones");
            createCache(cm, org.jhipster.blog.domain.Location.class.getName());
            createCache(cm, org.jhipster.blog.domain.Location.class.getName() + ".farms");
            createCache(cm, org.jhipster.blog.domain.Location.class.getName() + ".sensors");
            createCache(cm, org.jhipster.blog.domain.Device.class.getName());
            createCache(cm, org.jhipster.blog.domain.Device.class.getName() + ".plantFactories");
            createCache(cm, org.jhipster.blog.domain.Device.class.getName() + ".sensors");
            createCache(cm, org.jhipster.blog.domain.Device.class.getName() + ".climates");
            createCache(cm, org.jhipster.blog.domain.Device.class.getName() + ".irrigations");
            createCache(cm, org.jhipster.blog.domain.Device.class.getName() + ".dosings");
            createCache(cm, org.jhipster.blog.domain.Device.class.getName() + ".lights");
            createCache(cm, org.jhipster.blog.domain.DeviceLevel.class.getName());
            createCache(cm, org.jhipster.blog.domain.DeviceLevel.class.getName() + ".devices");
            createCache(cm, org.jhipster.blog.domain.DeviceModel.class.getName());
            createCache(cm, org.jhipster.blog.domain.DeviceModel.class.getName() + ".devices");
            createCache(cm, org.jhipster.blog.domain.Sensor.class.getName());
            createCache(cm, org.jhipster.blog.domain.SensorModel.class.getName());
            createCache(cm, org.jhipster.blog.domain.SensorModel.class.getName() + ".sensors");
            createCache(cm, org.jhipster.blog.domain.Plant.class.getName());
            createCache(cm, org.jhipster.blog.domain.Plant.class.getName() + ".recipes");
            createCache(cm, org.jhipster.blog.domain.Plant.class.getName() + ".crops");
            createCache(cm, org.jhipster.blog.domain.Product.class.getName());
            createCache(cm, org.jhipster.blog.domain.Product.class.getName() + ".crops");
            createCache(cm, org.jhipster.blog.domain.Seed.class.getName());
            createCache(cm, org.jhipster.blog.domain.Seed.class.getName() + ".lots");
            createCache(cm, org.jhipster.blog.domain.Breeder.class.getName());
            createCache(cm, org.jhipster.blog.domain.RawMaterial.class.getName());
            createCache(cm, org.jhipster.blog.domain.Recipe.class.getName());
            createCache(cm, org.jhipster.blog.domain.Season.class.getName());
            createCache(cm, org.jhipster.blog.domain.Season.class.getName() + ".crops");
            createCache(cm, org.jhipster.blog.domain.Season.class.getName() + ".calendars");
            createCache(cm, org.jhipster.blog.domain.Climate.class.getName());
            createCache(cm, org.jhipster.blog.domain.Irrigation.class.getName());
            createCache(cm, org.jhipster.blog.domain.PlantFactoryController.class.getName());
            createCache(cm, org.jhipster.blog.domain.Dosing.class.getName());
            createCache(cm, org.jhipster.blog.domain.Light.class.getName());
            createCache(cm, org.jhipster.blog.domain.Crop.class.getName());
            createCache(cm, org.jhipster.blog.domain.Crop.class.getName() + ".plants");
            createCache(cm, org.jhipster.blog.domain.Crop.class.getName() + ".calendars");
            createCache(cm, org.jhipster.blog.domain.Crop.class.getName() + ".scoutings");
            createCache(cm, org.jhipster.blog.domain.Crop.class.getName() + ".pestControls");
            createCache(cm, org.jhipster.blog.domain.Crop.class.getName() + ".diseaseControls");
            createCache(cm, org.jhipster.blog.domain.Crop.class.getName() + ".activities");
            createCache(cm, org.jhipster.blog.domain.Crop.class.getName() + ".harvests");
            createCache(cm, org.jhipster.blog.domain.Crop.class.getName() + ".lots");
            createCache(cm, org.jhipster.blog.domain.Calendar.class.getName());
            createCache(cm, org.jhipster.blog.domain.Calendar.class.getName() + ".weathers");
            createCache(cm, org.jhipster.blog.domain.Weather.class.getName());
            createCache(cm, org.jhipster.blog.domain.Scouting.class.getName());
            createCache(cm, org.jhipster.blog.domain.Scouting.class.getName() + ".pests");
            createCache(cm, org.jhipster.blog.domain.Scouting.class.getName() + ".diseases");
            createCache(cm, org.jhipster.blog.domain.Scouting.class.getName() + ".symptoms");
            createCache(cm, org.jhipster.blog.domain.Scouting.class.getName() + ".disorders");
            createCache(cm, org.jhipster.blog.domain.Pest.class.getName());
            createCache(cm, org.jhipster.blog.domain.Pest.class.getName() + ".pestControls");
            createCache(cm, org.jhipster.blog.domain.Pest.class.getName() + ".symptoms");
            createCache(cm, org.jhipster.blog.domain.PestControl.class.getName());
            createCache(cm, org.jhipster.blog.domain.Disease.class.getName());
            createCache(cm, org.jhipster.blog.domain.Disease.class.getName() + ".diseaseControls");
            createCache(cm, org.jhipster.blog.domain.Disease.class.getName() + ".symptoms");
            createCache(cm, org.jhipster.blog.domain.DiseaseControl.class.getName());
            createCache(cm, org.jhipster.blog.domain.Nutrient.class.getName());
            createCache(cm, org.jhipster.blog.domain.Activity.class.getName());
            createCache(cm, org.jhipster.blog.domain.Symptom.class.getName());
            createCache(cm, org.jhipster.blog.domain.Symptom.class.getName() + ".diseaseControls");
            createCache(cm, org.jhipster.blog.domain.Disorder.class.getName());
            createCache(cm, org.jhipster.blog.domain.Tool.class.getName());
            createCache(cm, org.jhipster.blog.domain.Tool.class.getName() + ".crops");
            createCache(cm, org.jhipster.blog.domain.Tool.class.getName() + ".calendars");
            createCache(cm, org.jhipster.blog.domain.Harvest.class.getName());
            createCache(cm, org.jhipster.blog.domain.Harvest.class.getName() + ".inspections");
            createCache(cm, org.jhipster.blog.domain.Lot.class.getName());
            createCache(cm, org.jhipster.blog.domain.Lot.class.getName() + ".harvests");
            createCache(cm, org.jhipster.blog.domain.Lot.class.getName() + ".inspectionRecords");
            createCache(cm, org.jhipster.blog.domain.Inspection.class.getName());
            createCache(cm, org.jhipster.blog.domain.Inspection.class.getName() + ".inspectionRecords");
            createCache(cm, org.jhipster.blog.domain.InspectionRecord.class.getName());
            createCache(cm, org.jhipster.blog.domain.Alert.class.getName());
            createCache(cm, org.jhipster.blog.domain.Reminder.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
