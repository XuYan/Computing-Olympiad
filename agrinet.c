/*
ID: Xu Yan
LANG: C
TASK: agrinet
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

static int MAX_INT = ~(1 << 31);
int adj_matrix[100][100]; // Since input scale is quite small, I declare 40 KB memory to store graph adjacency matrix representation
int distance[100]; // Similarly, I declare 400 bytes integer array to store the minimum distance from a farm to any farm in spanning tree
int inTree[100]; // I declare 400 bytes integer array to store the farms that are included in spanning tree (1 -> included, 0 -> not included yet)

int main() {
    FILE *fin, *fout;
    fin = fopen("agrinet.in", "r");
    fout = fopen("agrinet.out", "w");
    assert(fin != NULL && fout != NULL);

    int farm_count;
    fscanf(fin, "%d", &farm_count);

    int i, j;
    for (i = 0; i < farm_count; i++) {
    	for (j = 0; j < farm_count; j++) {
    		fscanf(fin, "%d", &adj_matrix[i][j]);
    	}
    }

    int total_fiber_length = 0;
    for (i = 0; i < farm_count; i++) {
    	distance[i] = MAX_INT;
    }
    distance[0] = 0;
    inTree[0] = 1;
    int farm_in_tree = 1;
    for (i = 1; i < farm_count; i++) {
    	distance[i] = adj_matrix[0][i];
    }

    // Algorithm Implementation
    while (farm_in_tree < farm_count) {
    	int nearest_tree_neighbor = -1;
    	int nearest_distance = MAX_INT;
    	for (i = 0; i < farm_count; i++) {
    		if (inTree[i] == 0 && distance[i] != MAX_INT && distance[i] < nearest_distance) {
   				nearest_distance = distance[i];
   				nearest_tree_neighbor = i;
    		}
    	}
    	inTree[nearest_tree_neighbor] = 1;
    	farm_in_tree ++;
    	total_fiber_length += nearest_distance;

    	for (i = 0; i < farm_count; i++) {
    		if (distance[i] > adj_matrix[nearest_tree_neighbor][i]) {
    			distance[i] = adj_matrix[nearest_tree_neighbor][i];
    		}
    	}
    }

    fprintf(fout, "%d\n", total_fiber_length);

    exit(0);
}
