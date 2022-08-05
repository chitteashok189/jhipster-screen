import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CalendarService } from '../service/calendar.service';
import { ICalendar, Calendar } from '../calendar.model';
import { IPlantFactory } from 'app/entities/plant-factory/plant-factory.model';
import { PlantFactoryService } from 'app/entities/plant-factory/service/plant-factory.service';
import { IZone } from 'app/entities/zone/zone.model';
import { ZoneService } from 'app/entities/zone/service/zone.service';
import { ICrop } from 'app/entities/crop/crop.model';
import { CropService } from 'app/entities/crop/service/crop.service';
import { ITool } from 'app/entities/tool/tool.model';
import { ToolService } from 'app/entities/tool/service/tool.service';
import { ISeason } from 'app/entities/season/season.model';
import { SeasonService } from 'app/entities/season/service/season.service';

import { CalendarUpdateComponent } from './calendar-update.component';

describe('Calendar Management Update Component', () => {
  let comp: CalendarUpdateComponent;
  let fixture: ComponentFixture<CalendarUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let calendarService: CalendarService;
  let plantFactoryService: PlantFactoryService;
  let zoneService: ZoneService;
  let cropService: CropService;
  let toolService: ToolService;
  let seasonService: SeasonService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CalendarUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CalendarUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CalendarUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    calendarService = TestBed.inject(CalendarService);
    plantFactoryService = TestBed.inject(PlantFactoryService);
    zoneService = TestBed.inject(ZoneService);
    cropService = TestBed.inject(CropService);
    toolService = TestBed.inject(ToolService);
    seasonService = TestBed.inject(SeasonService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PlantFactory query and add missing value', () => {
      const calendar: ICalendar = { id: 456 };
      const plantFactoryID: IPlantFactory = { id: 41345 };
      calendar.plantFactoryID = plantFactoryID;

      const plantFactoryCollection: IPlantFactory[] = [{ id: 11672 }];
      jest.spyOn(plantFactoryService, 'query').mockReturnValue(of(new HttpResponse({ body: plantFactoryCollection })));
      const additionalPlantFactories = [plantFactoryID];
      const expectedCollection: IPlantFactory[] = [...additionalPlantFactories, ...plantFactoryCollection];
      jest.spyOn(plantFactoryService, 'addPlantFactoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ calendar });
      comp.ngOnInit();

      expect(plantFactoryService.query).toHaveBeenCalled();
      expect(plantFactoryService.addPlantFactoryToCollectionIfMissing).toHaveBeenCalledWith(
        plantFactoryCollection,
        ...additionalPlantFactories
      );
      expect(comp.plantFactoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Zone query and add missing value', () => {
      const calendar: ICalendar = { id: 456 };
      const zoneID: IZone = { id: 26730 };
      calendar.zoneID = zoneID;

      const zoneCollection: IZone[] = [{ id: 90442 }];
      jest.spyOn(zoneService, 'query').mockReturnValue(of(new HttpResponse({ body: zoneCollection })));
      const additionalZones = [zoneID];
      const expectedCollection: IZone[] = [...additionalZones, ...zoneCollection];
      jest.spyOn(zoneService, 'addZoneToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ calendar });
      comp.ngOnInit();

      expect(zoneService.query).toHaveBeenCalled();
      expect(zoneService.addZoneToCollectionIfMissing).toHaveBeenCalledWith(zoneCollection, ...additionalZones);
      expect(comp.zonesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Crop query and add missing value', () => {
      const calendar: ICalendar = { id: 456 };
      const cropID: ICrop = { id: 88360 };
      calendar.cropID = cropID;

      const cropCollection: ICrop[] = [{ id: 53054 }];
      jest.spyOn(cropService, 'query').mockReturnValue(of(new HttpResponse({ body: cropCollection })));
      const additionalCrops = [cropID];
      const expectedCollection: ICrop[] = [...additionalCrops, ...cropCollection];
      jest.spyOn(cropService, 'addCropToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ calendar });
      comp.ngOnInit();

      expect(cropService.query).toHaveBeenCalled();
      expect(cropService.addCropToCollectionIfMissing).toHaveBeenCalledWith(cropCollection, ...additionalCrops);
      expect(comp.cropsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Tool query and add missing value', () => {
      const calendar: ICalendar = { id: 456 };
      const toolID: ITool = { id: 23574 };
      calendar.toolID = toolID;

      const toolCollection: ITool[] = [{ id: 2083 }];
      jest.spyOn(toolService, 'query').mockReturnValue(of(new HttpResponse({ body: toolCollection })));
      const additionalTools = [toolID];
      const expectedCollection: ITool[] = [...additionalTools, ...toolCollection];
      jest.spyOn(toolService, 'addToolToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ calendar });
      comp.ngOnInit();

      expect(toolService.query).toHaveBeenCalled();
      expect(toolService.addToolToCollectionIfMissing).toHaveBeenCalledWith(toolCollection, ...additionalTools);
      expect(comp.toolsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Season query and add missing value', () => {
      const calendar: ICalendar = { id: 456 };
      const seasonID: ISeason = { id: 89700 };
      calendar.seasonID = seasonID;

      const seasonCollection: ISeason[] = [{ id: 57150 }];
      jest.spyOn(seasonService, 'query').mockReturnValue(of(new HttpResponse({ body: seasonCollection })));
      const additionalSeasons = [seasonID];
      const expectedCollection: ISeason[] = [...additionalSeasons, ...seasonCollection];
      jest.spyOn(seasonService, 'addSeasonToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ calendar });
      comp.ngOnInit();

      expect(seasonService.query).toHaveBeenCalled();
      expect(seasonService.addSeasonToCollectionIfMissing).toHaveBeenCalledWith(seasonCollection, ...additionalSeasons);
      expect(comp.seasonsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const calendar: ICalendar = { id: 456 };
      const plantFactoryID: IPlantFactory = { id: 68023 };
      calendar.plantFactoryID = plantFactoryID;
      const zoneID: IZone = { id: 88971 };
      calendar.zoneID = zoneID;
      const cropID: ICrop = { id: 57380 };
      calendar.cropID = cropID;
      const toolID: ITool = { id: 32670 };
      calendar.toolID = toolID;
      const seasonID: ISeason = { id: 84623 };
      calendar.seasonID = seasonID;

      activatedRoute.data = of({ calendar });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(calendar));
      expect(comp.plantFactoriesSharedCollection).toContain(plantFactoryID);
      expect(comp.zonesSharedCollection).toContain(zoneID);
      expect(comp.cropsSharedCollection).toContain(cropID);
      expect(comp.toolsSharedCollection).toContain(toolID);
      expect(comp.seasonsSharedCollection).toContain(seasonID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Calendar>>();
      const calendar = { id: 123 };
      jest.spyOn(calendarService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ calendar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: calendar }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(calendarService.update).toHaveBeenCalledWith(calendar);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Calendar>>();
      const calendar = new Calendar();
      jest.spyOn(calendarService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ calendar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: calendar }));
      saveSubject.complete();

      // THEN
      expect(calendarService.create).toHaveBeenCalledWith(calendar);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Calendar>>();
      const calendar = { id: 123 };
      jest.spyOn(calendarService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ calendar });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(calendarService.update).toHaveBeenCalledWith(calendar);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackPlantFactoryById', () => {
      it('Should return tracked PlantFactory primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPlantFactoryById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackZoneById', () => {
      it('Should return tracked Zone primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackZoneById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCropById', () => {
      it('Should return tracked Crop primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCropById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackToolById', () => {
      it('Should return tracked Tool primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackToolById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackSeasonById', () => {
      it('Should return tracked Season primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSeasonById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
