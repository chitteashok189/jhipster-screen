import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PartyStatusItemService } from '../service/party-status-item.service';
import { IPartyStatusItem, PartyStatusItem } from '../party-status-item.model';

import { PartyStatusItemUpdateComponent } from './party-status-item-update.component';

describe('PartyStatusItem Management Update Component', () => {
  let comp: PartyStatusItemUpdateComponent;
  let fixture: ComponentFixture<PartyStatusItemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let partyStatusItemService: PartyStatusItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PartyStatusItemUpdateComponent],
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
      .overrideTemplate(PartyStatusItemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyStatusItemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    partyStatusItemService = TestBed.inject(PartyStatusItemService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const partyStatusItem: IPartyStatusItem = { id: 456 };

      activatedRoute.data = of({ partyStatusItem });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(partyStatusItem));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyStatusItem>>();
      const partyStatusItem = { id: 123 };
      jest.spyOn(partyStatusItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyStatusItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partyStatusItem }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(partyStatusItemService.update).toHaveBeenCalledWith(partyStatusItem);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyStatusItem>>();
      const partyStatusItem = new PartyStatusItem();
      jest.spyOn(partyStatusItemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyStatusItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partyStatusItem }));
      saveSubject.complete();

      // THEN
      expect(partyStatusItemService.create).toHaveBeenCalledWith(partyStatusItem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PartyStatusItem>>();
      const partyStatusItem = { id: 123 };
      jest.spyOn(partyStatusItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partyStatusItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(partyStatusItemService.update).toHaveBeenCalledWith(partyStatusItem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
