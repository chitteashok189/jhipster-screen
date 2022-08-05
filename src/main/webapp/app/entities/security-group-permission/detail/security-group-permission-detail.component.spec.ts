import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityGroupPermissionDetailComponent } from './security-group-permission-detail.component';

describe('SecurityGroupPermission Management Detail Component', () => {
  let comp: SecurityGroupPermissionDetailComponent;
  let fixture: ComponentFixture<SecurityGroupPermissionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SecurityGroupPermissionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ securityGroupPermission: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SecurityGroupPermissionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SecurityGroupPermissionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load securityGroupPermission on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.securityGroupPermission).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
