// MongoStatement.java

package com.mongodb.jdbc;

import java.sql.*;
import java.util.*;

import com.mongodb.*;

public class MongoStatement implements Statement {

    MongoStatement( MongoConnection conn , int type, int concurrency, int holdability){
        _conn = conn;
        _type = type;
        _concurrency = concurrency;
        _holdability = holdability;
        
        if ( _type != 0 )
            throw new UnsupportedOperationException( "type not supported yet" );
        if ( _concurrency != 0 )
            throw new UnsupportedOperationException( "concurrency not supported yet" );
        if ( _holdability != 0 )
            throw new UnsupportedOperationException( "holdability not supported yet" );
        
    }

    // --- batch ---
    
    public void addBatch(String sql){
        throw new UnsupportedOperationException( "batch not supported" );
    }
    public void clearBatch(){
        throw new UnsupportedOperationException( "batch not supported" );
    }
    public int[] executeBatch(){
        throw new UnsupportedOperationException( "batch not supported" );
    }
    
    // --- random
    
    public void cancel(){
        throw new RuntimeException( "not supported yet - can be" );
    }
    
    public void close(){
        _conn = null;
    }

    public Connection getConnection(){
        return _conn;
    }

    public boolean isClosed(){
        return _conn == null;
    }

    public boolean isPoolable(){
        return true;
    }

    public void setPoolable(boolean poolable){
        if ( ! poolable )
            throw new RuntimeException( "why don't you want me to be poolable?" );
    }
    
    public void clearWarnings(){
        throw new RuntimeException( "not supported yet - can be" );        
    }

    // --- writes ----
    
    public boolean execute(String sql){
        throw new RuntimeException( "execute not done" );
    }
    public boolean execute(String sql, int autoGeneratedKeys){
        throw new RuntimeException( "execute not done" );
    }
    public boolean execute(String sql, int[] columnIndexes){
        throw new RuntimeException( "execute not done" );
    }
    public boolean execute(String sql, String[] columnNames){
        throw new RuntimeException( "execute not done" );
    }

    public int executeUpdate(String sql)
        throws SQLException {
        return Executor.writeop( _conn._db , sql );
    }
    public int executeUpdate(String sql, int autoGeneratedKeys){
        throw new RuntimeException( "executeUpdate not done" );
    }
    public int executeUpdate(String sql, int[] columnIndexes){
        throw new RuntimeException( "executeUpdate not done" );
    }
    public int executeUpdate(String sql, String[] columnNames){
        throw new RuntimeException( "executeUpdate not done" );
    }

    public int getUpdateCount(){
        throw new RuntimeException( "getUpdateCount not done" );
    }

    public ResultSet getGeneratedKeys(){
        throw new RuntimeException( "getGeneratedKeys notn done" );
    }

    // ---- reads -----
    
    public ResultSet executeQuery(String sql)
        throws SQLException {
        // TODO
        // handle max rows

        DBCursor cursor = Executor.query( _conn._db , sql );
        if ( _fetchSize > 0 )
            cursor.batchSize( _fetchSize );
        if ( _maxRows > 0 )
            cursor.limit( _maxRows );
        
        _last = new MongoResultSet( cursor );
        return _last;
    }

    public int getQueryTimeout(){
        throw new RuntimeException( "query timeout not done" );
    }
    public void setQueryTimeout(int seconds){
        throw new RuntimeException( "query timeout not done" );
    }
    
    // ---- fetch modifiers ----


    public int getFetchSize(){
        return _fetchSize;
    }
    public void setFetchSize(int rows){
        _fetchSize = rows;
    }

    public int getMaxRows(){
        return _maxRows;
    }
    public void setMaxRows(int max){
        _maxRows = max;
    }
    
    public int getFetchDirection(){
        throw new RuntimeException( "fetch direction not done yet" );
    }
    public void setFetchDirection(int direction){
        throw new RuntimeException( "fetch direction not done yet" );
    }
        
    public int getMaxFieldSize(){
        throw new RuntimeException( "max field size not supported" );
    }
    public void setMaxFieldSize(int max){
        throw new RuntimeException( "max field size not supported" );
    }
    
    
    public boolean getMoreResults(){
        throw new RuntimeException( "getMoreResults not supported" );
    }
    public boolean getMoreResults(int current){
        throw new RuntimeException( "getMoreResults not supported" );
    }

    public ResultSet getResultSet(){
        return _last;
    }

    // ---- more random -----


    public SQLWarning getWarnings(){
        throw new UnsupportedOperationException( "warning not supported yet" );
    }

    public void setCursorName(String name){
        throw new UnsupportedOperationException( "can't set cursor name" );
    }
    
    public void setEscapeProcessing(boolean enable){
        if ( ! enable )
            throw new RuntimeException( "why do you want to turn escape processing off?" );
    }

    public int getResultSetConcurrency(){
        return _concurrency;
    }
    public int getResultSetHoldability(){
        return _holdability;
    }
    public int getResultSetType(){
        return _type;
    }

    public <T> T unwrap(Class<T> iface)
        throws SQLException {
        throw new UnsupportedOperationException();
    }

    public boolean isWrapperFor(Class<?> iface)
        throws SQLException {
        throw new UnsupportedOperationException();
    }

    private MongoConnection _conn;

    final int _type;
    final int _concurrency;
    final int _holdability;

    private int _fetchSize = 0;
    private int _maxRows = 0;

    private MongoResultSet _last;
}
