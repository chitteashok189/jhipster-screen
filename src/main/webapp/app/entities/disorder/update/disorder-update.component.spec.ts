import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DisorderService } from '../service/disorder.service';
import { IDisorder, Disorder } from '../disorder.model';
import { IScouting } from 'app/entities/scouting/scouting.model';
import { ScoutingService } from 'app/entities/scouting/service/scouting.service';

import { DisorderUpdateComponent } from './disorder-update.component';

describe('Disorder Management Update Component', () => {
  let comp: DisorderUpdateComponent;
  let fixture: ComponentFixture<DisorderUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let disorderService: DisorderService;
  let scoutingService: ScoutingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DisorderUpdateComponent],
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
      .overrideTemplate(DisorderUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DisorderUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    disorderService = TestBed.inject(DisorderService);
    scoutingService = TestBed.inject(ScoutingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Scouting query and add missing value', () => {
      const disorder: IDisorder = { id: 456 };
      const scoutingID: IScouting = { id: 44304 };
      disorder.scoutingID = scoutingID;

      const scoutingCollection: IScouting[] = [{ id: 52800 }];
      jest.spyOn(scoutingService, 'query').mockReturnValue(of(new HttpResponse({ body: scoutingCollection })));
      const additionalScoutings = [scoutingID];
      const expectedCollection: IScouting[] = [...additionalScoutings, ...scoutingCollection];
      jest.spyOn(scoutingService, 'addScoutingToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ disorder });
      comp.ngOnInit();

      expect(scoutingService.query).toHaveBeenCalled();
      expect(scoutingService.addScoutingToCollectionIfMissing).toHaveBeenCalledWith(scoutingCollection, ...additionalScoutings);
      expect(comp.scoutingsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const disorder: IDisorder = { id: 456 };
      const scoutingID: IScouting = { id: 9782 };
      disorder.scoutingID = scoutingID;

      activatedRoute.data = of({ disorder });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(disorder));
      expect(comp.scoutingsSharedCollection).toContain(scoutingID);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Disorder>>();
      const disorder = { id: 123 };
      jest.spyOn(disorderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ disorder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: disorder }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(disorderService.update).toHaveBeenCalledWith(disorder);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Disorder>>();
      const disorder = new Disorder();
      jest.spyOn(disorderService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ disorder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: disorder }));
      saveSubject.complete();

      // THEN
      expect(disorderService.create).toHaveBeenCalledWith(disorder);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Disorder>>();
      const disorder = { id: 123 };
      jest.spyOn(disorderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ disorder });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(disorderService.update).toHaveBeenCalledWith(disorder);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackScoutingById', () => {
      it('Should return tracked Scouting primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackScoutingById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
