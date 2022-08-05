jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { PartyRelationshipService } from '../service/party-relationship.service';

import { PartyRelationshipDeleteDialogComponent } from './party-relationship-delete-dialog.component';

describe('PartyRelationship Management Delete Component', () => {
  let comp: PartyRelationshipDeleteDialogComponent;
  let fixture: ComponentFixture<PartyRelationshipDeleteDialogComponent>;
  let service: PartyRelationshipService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PartyRelationshipDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(PartyRelationshipDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PartyRelationshipDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PartyRelationshipService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
