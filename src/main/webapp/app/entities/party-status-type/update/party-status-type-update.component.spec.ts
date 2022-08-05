import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PartyStatusTypeService } from '../service/party-status-type.service';
import { IPartyStatusType, PartyStatusType } from '../party-status-type.model';

import { PartyStatusTypeUpdateComponent } from './party-status-type-update.component';

describe('PartyStatusType Management Update Component', () => {
  let comp: PartyStatusTypeUpdateComponent;
  let fixture: ComponentFixture<PartyStatusTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let partyStatusTypeService: PartyStatusTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PartyStatusTypeUpdateComponent],
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
      .overrideTemplate(PartyStatusTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyStatusTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    partyStatusTypeService = TestBed.inject(PartyStatusTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const partyStatusType: IPartyStatusType = { id: 456 };

      activatedRoute.data = of({ partyStatusType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(partyStatusType));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyStatusType>>();
      const partyStatusType = { id: 123 };
      jest.spyOn(partyStatusTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyStatusType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partyStatusType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(partyStatusTypeService.update).toHaveBeenCalledWith(partyStatusType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyStatusType>>();
      const partyStatusType = new PartyStatusType();
      jest.spyOn(partyStatusTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyStatusType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partyStatusType }));
      saveSubject.complete();

      // THEN
      expect(partyStatusTypeService.create).toHaveBeenCalledWith(partyStatusType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyStatusType>>();
      const partyStatusType = { id: 123 };
      jest.spyOn(partyStatusTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyStatusType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(partyStatusTypeService.update).toHaveBeenCalledWith(partyStatusType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
