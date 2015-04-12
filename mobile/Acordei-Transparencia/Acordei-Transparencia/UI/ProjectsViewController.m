//
//  ProjectsViewController.m
//  Acordei-Transparencia
//
//  Created by Giovane Possebon on 11/4/15.
//  Copyright (c) 2015 acordei. All rights reserved.
//

#import "ProjectsViewController.h"
#import "ProjectsCell.h"
#import "ApiCall.h"

@interface ProjectsViewController ()

@end

@implementation ProjectsViewController
{
    NSArray *projects;
    NSInteger arquivada;
    NSInteger aprovada;
    NSInteger reprovada;
    
}

- (void)viewDidLoad {
    [super viewDidLoad];
    NSString *nomeParlamentar = [self.fromListArray valueForKey:@"nomeParlamentar"];
    dispatch_async(dispatch_get_global_queue( DISPATCH_QUEUE_PRIORITY_LOW, 0), ^{
        [[[ApiCall alloc] init] callWithUrl:[NSString stringWithFormat:@"http://acordei.cloudapp.net:80/api/politico/projetos?nomePolitico=%@", nomeParlamentar]
                            SuccessCallback:^(NSData *message){
                                projects = [self populateProjects:message];
                                
                                dispatch_async(dispatch_get_main_queue(), ^{
                                    NSLog(@"%@", projects);
                                    [self.collectionView reloadData];
                                });
                            }ErrorCallback:^(NSData *erro){
                                NSLog(@"Veio do WS essa mensagem de erro: %@",erro);
                            }];
    });
    // Do any additional setup after loading the view.
}

-(void)getNumbers:(NSArray *)array{
    self.lblNumberApproved.text = [array valueForKey:@"propostasAprovadas"];
    self.lblNumberArchived.text = [array valueForKey:@"propostasArquivadas"];
    self.lblNumberRejected.text = [array valueForKey:@"propostasRejeitadas"];
}

-(NSArray *)populateProjects:(NSData *)data{
    NSError *error;
    return [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:&error];
}

-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
    return projects.count;
}

-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath {
    ProjectsCell *cell = [self.collectionView dequeueReusableCellWithReuseIdentifier:@"cell" forIndexPath:indexPath];
    
    if ([[[[projects objectAtIndex:indexPath.row]valueForKey:@"projetos"]valueForKey:@"situacao"]isEqualToString:@"Arquivada"]) {
        cell.imgStatus.image = [UIImage imageNamed:@"icon-archived.png"];
    }
    
    else if ([[[projects objectAtIndex:indexPath.row]valueForKey:@"situacao"]isEqualToString:@"Aprovada"]) {
        cell.imgStatus.image = [UIImage imageNamed:@"green-check.png"];
    }
    
    else if ([[[projects objectAtIndex:indexPath.row]valueForKey:@"situacao"]isEqualToString:@"Rejeitada"]) {
        cell.imgStatus.image = [UIImage imageNamed:@"icon-reproved.png"];
    }
    
    cell.lblProject.text = [[[projects objectAtIndex:indexPath.row]valueForKey:@"projetos"]valueForKey:@"descricao"];
    
    return cell;
}


@end
